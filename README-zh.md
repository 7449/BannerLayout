# BannerLayoutSimple

支持图片无限轮播的BannerLayout

1.x版本相比0.x版本修改幅度较大

##支持功能


- 可自定义小圆点（左中右）,title（左中右）,提示栏（上中下）位置

- 可自定义小圆点，以及是否自动轮播，轮播时间

- 支持List 、数组 两种数据格式（网络本地都支持）

- 支持点击事件以及轮播速度及viewPager滑动切换速度

- 支持是否显示小圆点，title,或者整个提示栏

- 支持加载时和加载失败时显示自定义图片

- 支持选择暂停 恢复 轮播状态

- 支持自定义提示栏（不建议使用）

- 支持动画(随机动画需要List动画集合)

- 支持自定义Bean类，如果只是简单使用，可以使用系统默认的BannerBean

- 支持垂直滚动，使用动画实现，所以垂直滚动不能使用动画

####使用效果

>simple只有第一个示例默认开启轮播，其余的示例不开启轮播,simple录制有点丢帧

![](http://i.imgur.com/yLQUFvQ.gif)

##基础使用方法

>项目中引用 

		compile 'com.ydevelop:bannerlayout:1.0.0'

>更新状态

	1.0.0 ： bannerlayout重构，使用相比0.0.X版本更简单
	0.0.8：添加垂直滚动的动画，代码逻辑优化
	...


>如果是网络加载图片 记得添加

	<uses-permission android:name="android.permission.INTERNET" />

>简单使用方式

            holder.getBannerLayout()
                    .initListResources(initImageModel())//初始化数据
                    .initTips(true, true, true, BannerTipsSite.TOP, null, null)//设置tips
                    .start(true, 2000)//轮播 轮播时间

>细节问题

	一些TipsLayout设置  比如字体大小 颜色之类的就要放在initTips之前调用，
	1.x版本在0.x版本的基础上去掉了手动调用initAdapter()，放在了初始化数据之后主动调用，
	所以ViewPager的一些方法就要放在初始化数据之前调用，例如滑动速度 是否竖直滑动 自定义提示栏。

1.数组方式

>数组使用也是在内部转化成List数据，所以点击事件以及自定义ImageLoaderManager传递的泛型均为BannerModel

        Object[] mImage = ;
        String[] mTitle = ;
        holder.getBannerLayout()
                .initArrayResources(mImage, mTitle)
                .initTips(true, true, true, BannerTipsSite.BOTTOM, BannerDotsSite.LEFT, BannerTitleSite.RIGHT);

2.List集合

        List<BannerModel> mDatas = new ArrayList<>();
		...
        bannerLayout
                .initImageListResources(mDatas)
                .initTips()
                .start(true);	

3.点击事件

>如果不传递泛型，返回的model就是当前Bean类，强转即可，建议传递泛型


            bannerLayout
                    .initListResources(initImageModel())
                    .setOnBannerClickListener(new OnBannerClickListener<ImageModel>() {

                        @Override
                        public void onBannerClick(int position, ImageModel model) {
                            Toast.makeText(holder.getContext(), model.getTestText(), Toast.LENGTH_SHORT).show();
                        }
                    });

4.提示栏及小圆点、title位置的改变

	想要改变位置在initTips()方法中实现几种不同的状态，不需要的可以直接传null 有默认的参数

	BannerTipsSite 	 			提示栏在布局中的位置，TOP,BUTTOM,CENTERED三种可选 
	BannerDotsSite  			小圆点在提示栏的位置，LEFT,CENTERED,RIGHT三种可选 
	BannerTitleSite  			title在提示栏的位置，LEFT,CENTERED,RIGHT三种可选 

5.使用自定义Bean类
	
>因为内置的Bean类只是简单的加载image和title，如果点击事件要传递ID之类的参数，那么就只能自定义一个Bean类

	自定义Bean类大致分为二种情况：
		1.后台的参数url和title刚好是内置Bean的image,title.命名方式一样，那么可以直接使用.
		2.后台参数的imageUrl和Title只要有任何一个和内置Bean的命名不一样的，就必须要自定义ImageLoaderManage,因为BannerLayout默认的是获取BannerModel里面的image和title，除非你和后台协商好，让他把命名改一下

	这两种情况下自定义Bean都必须要继承BannerModel这个类，否则BannerLayout不会识别出来，至于自定义ImageLoaderManager请看第六条

	如果提示栏的文字的命名不是title,那么请实现OnBannerTitleListener，返回具体的title即可

	自定义Bean类完整示例：
		 bannerLayout
                    .setImageLoaderManager(new ImageManager())
                    .addOnBannerTitleListener(new OnBannerTitleListener() {
                        @Override
                        public String getTitle(int newPosition) {
                            return initBannerBean().get(newPosition).getThisTitle();
                        }
                    })
                    .initImageListResources(initBannerBean())
                    .initTips(true, true, true);

6.使用自定义加载图片框架

>BannerLayout内部引用Glide3.7.0，如果不想在你的项目中使用这个版本，请用exclude将它排除掉，再自行引入你使用的版本
	  
	默认使用Glide加载图片，如果不喜欢的继承 ImageLoaderManager 然后在代码中 setImageLoaderManager.

	 bannerLayout
                .initImageListResources(mBanner)
                .setImageLoaderManage(new ImageLoader()) //自己定义加载图片的方式
                .initTips(true, true, false)
                .start(true);

	public class ImageManager implements ImageLoaderManager<BannerBean> {
	
	    @Override
	    public void display(Context context, ImageView imageView, BannerBean model) {
	        Picasso.with(context)
	                .load(model.getImageUrl())
	                .placeholder(R.mipmap.ic_launcher)
	                .error(R.mipmap.ic_launcher)
	                .into(imageView);
	    }
	}

7.切换动画以及速度

>垂直滚动的动画

viewpager的垂直这里用的是动画，所以只要选择了垂直滚动，设置动画无效

动画内置的 [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)，多谢作者

	
	如果想自定义动画请继承 ABaseTransformer 或者 BannerTransformer 即可;
	
	        bannerLayout
	                .initImageListResources(list) //自定义model类
	                .initTips()
	                .setBannerTransformer(new FlipVerticalTransformer())  //切换动画效果
	                .setBannerTransformerList(transformers) //开启随机动画,这里设置，那就没必要设置切换动画效果了，需要一个list动画集合
	                .setDuration(3000) //切换速度
	                .start();
	
	如果只想使用内置的动画可以用 BannerAnimationType 进行选择
	
	例：
	
		   bannerLayout
	                .initImageListResources(list) //自定义model类
	                .initTips()
	                .setBannerTransformer(BannerAnimationType.CUBE_IN)
	                .start();

8.动画集合：


>自定义动画集合

        List<BannerTransformer> transformers = new ArrayList<>();
       
		bannerLayout.setBannerTransformerList(transformers);

>系统动画集合

		 List<BannerAnimationType> enumTransformer = new ArrayList<>();

		bannerLayout.setBannerSystemTransformerList(enumTransformer);

9.自定义提示栏

>自定义提示栏不建议使用，没有什么能快速设置的功能请尽量提[lssues](https://github.com/7449/BannerLayoutSimple/issues)

        bannerLayout
                .initImageListResources(mDatas)
                .addOnBannerPageChangeListener(new BannerOnPage())
				.addPromptBar(new PromptBarView(getBaseContext())) 
                .start(true);

     /**
     * 接管viewpager的onPage方法
     */
    public class BannerOnPage implements OnBannerPageChangeListener {

	    @Override
	    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	
	    }
	
	    @Override
	    public void onPageSelected(int position) {
	    }
	
	    @Override
	    public void onPageScrollStateChanged(int state) {
	
	    }
	}

>最后调用start()的时候可以决定是否开启自动轮播，不管在fragment还是activity里面，应该在合适的生命周期里选择暂停或者恢复轮播（如果开启了自动轮播），BannerLayout已经提供了方法，使用者直接调用就可以了



## 自定义参数详解

属性名								|说明  						|属性值
---    								|---   						|---
delay_time   						|轮播时间					|默认2s
start_rotation   					|是否开启自动轮播				|true 开启，默认不开启
view_pager_touch_mode   			|viewpager是否可以手动滑动	|true禁止滑动,false可以滑动，默认可以滑动
banner_duration						|viewPager切换速度			|默认800，越大越慢
banner_isVertical					|viewPager垂直滚动			|默认不是垂直滚动，true开启
dots_visible		  				|是否显示小圆点				|默认显示
dots_selector   					|小圆点状态选择器				|可参考自带的
dots_left_margin	   				|小圆点的marginLeft			|默认10	
dots_right_margin   				|小圆点的marginRight			|默认10	
dots_width   						|小圆点width					|默认15
dots_height   						|小圆点height				|默认15
is_tips_background					|是否显示提示控件的背景		|true 显示，默认不显示
tips_background				   		|BannerTips背景色			|默认半透明色
tips_width				   			|BannerTips宽度				|填充屏幕
tips_height			 				|BannerTips高度				|默认50
glide_error_image  					|glide加载错误占位符			|默认android自带图标
glide_place_image  					|glide加载中占位符			|默认android自带图标
title_visible		  				|是否显示title				|默认不显示
title_size			   				|字体大小					|默认12
title_color		 					|字体颜色					|默认黄色
title_width		 					|字体width					|默认自适应
title_height		 				|字体height					|默认自适应
title_left_margin   				|title marginLeft			|默认10	
title_right_margin   				|title marginRight			|默认10	


License
--
    Copyright (C) 2016 yuezhaoyang7449@163.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
