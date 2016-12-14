# BannerLayoutSimple

BannerLayout for unlimited rotation of images

[中文文档](https://github.com/7449/BannerLayout/blob/master/README-zh.md)

##Support function


- can be customized small dots (left center), title (left center), prompt bar (upper middle) position

- you can customize small dots, and whether to automatically rotate to the next page and rotation time

- support List, array two data formats (network local support)

- support for click events and rotation speed and viewPager slide switch speed

- whether to display a small dot, title, or the entire tip column

- support for loading and loading fails to display custom pictures

- supports pause to resume the rotation status

- support for custom hints (not recommended)

- support animation (random animation needs List animation collection)

- support for custom Bean class, if only simple to use, you can use the system default BannerBean

####Effect

>Simple only the first example of the default open carousel, the rest of the examples do not open the carousel, simple recording a bit dropped frames

![](http://i.imgur.com/gCZhj2M.gif)

##Basic Usage

>gradle

		compile 'com.ydevelop:bannerlayout:0.0.8'

>Update log

	0.0.8: add vertical scrolling animation, code logic optimization

	0.0.7：Improve part of the logic, solve the problem of custom Bean class title

	0.0.6：Modify part of the code, the array format from int to object, rewrite simple
	
	0.0.5：Add animation, support for custom animation, the system comes with dozens of animation, support for random animation

	0.0.4： Support custom Model class, custom needs to inherit BaseModel, fix 0.0.3 can not click the bug
		
	0.0.3：Add the last forgot to add some methods to support the custom prompt bar, if the custom prompt column do not initialize initRound ()

	0.0.2：Modify part of the code, loading pictures can choose to customize the load frame or use the default Glide

	0.0.1：Submit the project


>If the network is loading pictures remember to add

	<uses-permission android:name="android.permission.INTERNET" />

1.Array (from int to object, in order to adapt to different types)

        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.bannerLayout);
        Object[] mImage = new Object[]{"http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", R.drawable.banner2, R.drawable.banner3};
        String[] mTitle = new String[]{"bannerl", "banner2", "banner3"};
        bannerLayout
                .initImageArrayResources(mImage, mTitle)
                .initAdapter()
                .initRound(true, true, true)
                .start(true);

2.ArrayList

        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.bannerLayout);
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "那个时候刚恋爱，这个时候放分手"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "羞羞呢～"));
        mDatas.add(new BannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "腿不长 但细"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "深夜了"));
        bannerLayout
                .initImageListResources(mDatas)
                .initAdapter()
                .initRound()
                .start(true);	

3.Click the event, you can also write their own separate, if the list is the collection of the current return model object, if the array is returned to the current array of object is the object, the return of the object inside the network url
Custom model If you get the json picture is not named image, please realize your ImageLoaderManage


		bannerLayout
                .initImageListResources(list) 
                .initAdapter()
                .initRound()
                .start(true)
                .setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position, Object model) {
                        ImageModel imageModel = (ImageModel) model;
                        Toast.makeText(getApplicationContext(), imageModel.getTestText(), Toast.LENGTH_SHORT).show();
                        //If the object is returned to the object array is passed into the object
	//                        int[] image = (int[]) model;
	//                        Toast.makeText(getApplicationContext(), image[position], Toast.LENGTH_SHORT).show();

                    }
                })
                });

4.Tip column and small dots, title position changes

	Want to change the position in the initRound () method to achieve several different states, do not need to pass directly to the null default parameters

	BANNER_TIP_LAYOUT_POSITION 	 	The location of the tip bar in the layout，TOP,BUTTOM,CENTERED Three optional 
	BANNER_ROUND_POSITION  			Small dots in the location of the prompt bar，LEFT,CENTERED,RIGHT Three optional
	BANNER_TITLE_POSITION  			Title The location of the prompt bar，LEFT,CENTERED,RIGHT Three optional

5.Customize the Bean class

>Because the built-in Bean class simply loads image and title, if the click event to pass the ID and the like parameters, then you can only customize a Bean class
	
	Custom Bean class can be broadly divided into two cases:：
		1.The background parameters url and title is the built-in Bean's image, title. Named the same way, then you can directly use.
		2.ImageUrl and Title of background parameters As long as any of the names and built-in Bean is not the same, you must customize the ImageLoaderManage, because BannerLayout acquiescence acquires BannerModel inside the image and title, unless you and the background consultation, let him name Change it

	In both cases, custom Bean must inherit the BannerModel class, otherwise BannerLayout will not recognize it. As for custom ImageLoaderManage, see article 6

	In the second case, ImageLoaderManage inside the url will be directly returned to null, please note that this, when loading, please use the model, because the model is your incoming Bean object

	If the title bar text is not named title, then the realization of OnBannerTitleListener, can return to the specific title

	A complete example of a custom Bean class：

		 holder.getBannerLayout()
                    .setImageLoaderManage(new ImageManager())
                    .addOnBannerTitleListener(new OnBannerTitleListener() {
                        @Override
                        public String getTitle(int newPosition) {
                            return initBannerBean().get(newPosition).getThisTitle();
                        }
                    })
                    .initImageListResources(initBannerBean())
                    .initAdapter()
                    .initRound(true, true, true);

6.Use the Custom Load Picture frame
	  
	The default is to use Glide to load the image if you do not like the inheritance of ImageLoaderManage and then setImageLoaderManage in the code.

	 bannerLayout
                .initImageListResources(mBanner)
                .setImageLoaderManage(new ImageLoader()) //Own definition of loading pictures
                .initAdapter()
                .initRound(true, true, false)
                .start(true);

	Glide acquiescence even if the local resources can also load the document, but Picasso loading not, if you use Picasso loading pictures please strong url into int type, the other has not tried.

	    public class ImageLoader implements ImageLoaderManage {

	        @Override
	        public void display(Context context, ImageView imageView, Object url, Object model) {
				//If the list is set to return to the current model is the Model object, if it is an array, the return model is the current array of objects
	            Picasso.with(context).load((int) url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
	        }
	    }

7.Toggle animation and speed

>a vertical scrolling animation

Viewpager vertical Here is the use of animation, so long as the choice of vertical scrolling, setting animation invalid


	Animation built-in [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)，Thank the author
	
	If you want to customize the animation, please inherit ABaseTransformer or BannerTransformer ;
	
	        bannerLayout
	                .initImageListResources(list) //Customize the model class
	                .initAdapter()
	                .initRound(true, true, true, null, BANNER_ROUND_POSITION.LEFT, BANNER_TITLE_POSITION.CENTERED)
	                .setBannerTransformer(new FlipVerticalTransformer())  //Toggle animations
	                .setBannerTransformerList(transformers) //Open random animation, set here, then there is no need to set the switch animation, the need for a list of animation collection
	                .setDuration(3000) //Switching speed
	                .start();
	
	If you only want to use the built-in animation can use BANNER_ANIMATION to choose
	
	simple：
	
		   bannerLayout
	                .initImageListResources(list) //Customize the model class
	                .initAdapter()
	                .initRound(true, true, true, null, BANNER_ROUND_POSITION.LEFT, BANNER_TITLE_POSITION.CENTERED)
	                .setBannerTransformer(BANNER_ANIMATION.CUBE_IN)
	                .start();
	
8.Animation collection：
	
	>Customize the animation collection
	
	        List<BannerTransformer> transformers = new ArrayList<>();
	       
			bannerLayout.setBannerTransformerList(transformers);
	
	>A collection of system animations
	
			 List<BANNER_ANIMATION> enumTransformer = new ArrayList<>();
	
			bannerLayout..setBannerSystemTransformerList(enumTransformer);

9.Customize the tip bar

>Custom tip bar is not recommended, for ease of packaging, if you use a custom prompt bar against the original intention, so there is no quick set of features please mention [lssues](https://github.com/7449/BannerLayout/issues) Unless it is a very wonderful demand, and then use the custom prompt bar

        bannerLayout
                .initImageListResources(mDatas)
                .addOnBannerPageChangeListener(new BannerOnPage())
				.addPromptBar(new PromptBarView(getBaseContext())) //The custom hint bar view takes effect before the initAdapter call
                .initAdapter()
                .start(true);

     /**
     * Take over the onPage method of the viewpager
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

>BannerLayout already provides a method that can be used to determine whether rotation is enabled, whether in a fragment or in an activity, by choosing to pause or resume rotation in the appropriate lifecycle (if autoscroll is enabled) Users can directly call, if you use the List data, please use the BannerModel



## Custom parameters explained in detail

The property name					|Description  												|The attribute value
---    								|---   														|---
delay_time   						|Rotation time												|default 2s
start_rotation   					|Whether to turn on automatic rotation						|True on, the default is not open
view_pager_touch_mode   			|Whether the viewpager can be manually swiped				|True to prohibit sliding, false can slide, the default can slide
round_selector   					|Small Dot State Selector									|Can refer to their own
round_container_background_switch   |Whether to display the background of the prompt control	|True display, the default is not displayed
round_left_margin   				|The dots are marginLeft									|default 10	
round_right_margin   				|The dots are marginRight									|default 10	
title_left_margin   				|title marginLeft											|default 10	
title_right_margin   				|title marginRight											|default 10	
round_width   						|The width of the dots										|default 15
round_height   						|The height of the dots										|default 15
round_container_background   		|BannerRound Background color								|default translucent
round_container_width   			|BannerRound width											|default wrap_content
round_container_height 				|BannerRound height											|default 50
glide_error_image  					|Glide Load error placeholder								|defaultandroid Comes with icons
glide_place_image  					|Placeholder in Glide loading								|defaultandroid Comes with icons
banner_round_visible  				|Whether to display small dots								|default display
banner_title_visible  				|Whether title is displayed									|default not displayed
banner_title_size   				|font size													|default 12
banner_title_color 					|font color													|default yellow
banner_title_width 					|font width													|default wrap_content
banner_title_height 				|font height												|default wrap_content
banner_duration						|ViewPager switch speed										|default 800，The bigger the slower
banner_isVertical					|The viewPager scrolls vertically							|The default is not vertical scrolling, true on

#At last
	
BannerLayout This category inside the Notes I feel very detailed, if the above settings do not know how to look at BannerLayout.

I am sure a person can not measure all the bugs, so now I do not know where there are problems, the basic use of the time being found no problems

If someone in the course of the use of unknown or inexplicable bug, welcome to mention [lssues](https://github.com/7449/BannerLayoutSimple/issues),
As for the image loading I was directly built with Glide to load the images. Regardless of local or network images can be, but remember to add network permissions

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
