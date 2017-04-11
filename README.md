# BannerLayoutSimple

BannerLayout for unlimited rotation of images


[中文文档](https://7449.github.io/2016/10/26/Android_BannerLayout/)

## Support function


- can be customized  dots , title, prompt bar position，Support for custom selectors

- you can customize small dots, and whether to automatically rotate to the next page and rotation time

- support for click events and rotation speed and viewPager slide switch speed

- whether to display a dots, title, or the entire tip column

- support for loading and loading fails to display custom pictures

- supports pause to resume the rotation status

- supports animation and vertical scrolling

#### Effect

![](http://i.imgur.com/WnrNvI4.gif)

## Basic Usage

>gradle

    compile 'com.ydevelop:bannerlayout:1.1'
	
>If the network is loading pictures remember to add

	<uses-permission android:name="android.permission.INTERNET" />

>Simple to use


Bean class please implement `BannerModelCallBack`

Specific reference `SimpleBannerModel`

        holder.getBannerLayout()
                .initListResources(initImageModel())//initData
                .initTips(true, true, true)//settings tips
                .start(true, 2000)

If you use the built-in frame, please rely on Glide

Because in the frame

    provided 'com.github.bumptech.glide:glide:3.7.0'

>Calling start () can decide whether to open automatic rotation, if you turn on the automatic rotation should be in the appropriate life cycle, choose to pause or resume rotation

	startBanner(); //Start rotation
	stopBanner(); //Paused rotation
	restoreBanner(); //Resume rotation

 0 . pageNumberView:

           bannerLayout
                    .initPageNumView();
1.List

    List<BannerModel> mDatas = new ArrayList<>();
    ...
    bannerLayout
            .initImageListResources(mDatas)
            .initTips()
            .start(true);

3.Click the event

>If you do not pass generics, the return model is the current Bean class, strong turn can be recommended to pass generics

        bannerLayout
                .initListResources(initImageModel())
                .setOnBannerClickListener(new OnBannerClickListener<ImageModel>() {

                    @Override
                    public void onBannerClick(View view, int position, ImageModel model) {
                        Toast.makeText(view.getContext(), model.getTestText(), Toast.LENGTH_SHORT).show();
                    }
                });

4.Tip column and small dots, title position changes

	setTipsSite()               	 	The location of the tip bar in the layout，top,bottom,center Three optional 
	setDotsSite()               		dots in the location of the prompt bar，left,center,right Three optional
	setTitleSite()               		Title The location of the prompt bar，left,center,right Three optional

	xml:
		    <com.bannerlayout.widget.BannerLayout
		        ...
		        app:tips_site="center" />

5.Use the Custom Load Picture frame
	  
	The default is to use Glide to load the image if you do not like the inheritance of ImageLoaderManage and then setImageLoaderManage in the code.

	 bannerLayout
                .initImageListResources(mBanner)
                .setImageLoaderManage(new ImageLoader()) //Own definition of loading pictures
                .initTips(true, true, false)
                .start(true);

	public class ImageManager implements ImageLoaderManager<BannerBean> {
	
	    @Override
	    public void display(ImageView imageView, BannerBean model) {
	        Picasso.with(imageView.getContext())
	                .load(model.getImageUrl())
	                .placeholder(R.mipmap.ic_launcher)
	                .error(R.mipmap.ic_launcher)
	                .into(imageView);
	    }
	}

6.Toggle animation and speed

>a vertical scrolling animation

Viewpager vertical Here is the use of animation, so long as the choice of vertical scrolling, setting animation invalid


Animation built-in [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)，Thank the author
	
	If you want to customize the animation, please inherit ABaseTransformer or BannerTransformer ;
	
	        bannerLayout
	                .initImageListResources(list) //Customize the model class
	                .initTips()
	                .setBannerTransformer(new FlipVerticalTransformer())  //Toggle animations
	                .setBannerTransformerList(transformers) //Open random animation, set here, then there is no need to set the switch animation, the need for a list of animation collection
	                .setDuration(3000) //Switching speed
	                .start();
	
	If you only want to use the built-in animation can use BannerAnimation to choose
	
	simple：
	
		   bannerLayout
	                .initImageListResources(list) //Customize the model class
	                .initTips()
	                .setBannerTransformer(BannerAnimation.CUBE_IN)
	                .start();
	
7.Animation collection：
	
	>Customize the animation collection
	
	        List<BannerTransformer> transformers = new ArrayList<>();
	       
			bannerLayout.setBannerTransformerList(transformers);
	
	>A collection of system animations
	
			 List<BannerAnimation> enumTransformer = new ArrayList<>();
	
			bannerLayout..setBannerSystemTransformerList(enumTransformer);

## Custom parameters explained in detail

The property name					|Description  												|The attribute value
---    								|---   														|---
delay_time   						|Rotation time												|default 2s
start_rotation   					|Whether to turn on automatic rotation						|True on, the default is not open
view_pager_touch_mode   			|Whether the viewpager can be manually swiped				|True to prohibit sliding, false can slide, the default can slide
banner_duration						|ViewPager switch speed										|default 800，The bigger the slower
banner_isVertical					|The viewPager scrolls vertically							|The default is not vertical scrolling, true on
dots_visible		  				|Whether to display small dots								|default display
dots_selector   					|Small Dot State Selector									|Can refer to their own
dots_left_margin	   				|The dots are marginLeft									|default 10	
dots_right_margin   				|The dots are marginRight									|default 10	
dots_width   						|The width of the dots										|default 15
dots_height   						|The height of the dots										|default 15
is_tips_background				 	|Whether to display the background of the prompt control	|True display, the default is not displayed
tips_background				   		|BannerRound Background color								|default translucent
tips_width				   			|BannerRound width											|default wrap_content
tips_height			 				|BannerRound height											|default 50
glide_error_image  					|Glide Load error placeholder								|defaultandroid Comes with icons
glide_place_image  					|Placeholder in Glide loading								|defaultandroid Comes with icons
title_visible  						|Whether title is displayed									|default not displayed
title_size   						|font size													|default 12
title_color 						|font color													|default yellow
title_width 						|font width													|default wrap_content
title_height 						|font height												|default wrap_content
title_left_margin   				|title marginLeft											|default 10	
title_right_margin   				|title marginRight											|default 10	
enabledRadius						|enabledDots Radius  										|default 20f
normalRadius						|normalDots Radius  										|default 20f
enabledColor						|enabledDots color											|default blue
normalColor							|normalDots color											|default white
tips_site							|Tips in the layout position    							|Default bottom, optional upper and lower
dots_site							|The position of the dots in the layout    					|Default bottom, optional left center
title_site							|Title The position in the layout    						|Default bottom, optional left center
page_num_view_radius				|pageNumView shape radius  		 							|Default 25f
page_num_view_paddingTop			|pageNumView padding Top									|Default 5
page_num_view_paddingLeft			|pageNumView padding Left									|Default 20
page_num_view_paddingRight			|pageNumView padding Right									|Default 20
page_num_view_paddingBottom			|pageNumView padding Bottom									|Default 5
page_num_view_marginTop				|pageNumView margin 	 									|Default 0
page_num_view_marginLeft			|pageNumView margin		  									|Default 0
page_num_view_marginRight			|pageNumView margin  										|Default 0
page_num_view_marginBottom			|pageNumView margin  										|Default 0
page_num_view_textColor				|pageNumView textColor	 									|Default white
page_num_view_BackgroundColor		|pageNumView BackgroundColor								|Default translucent
page_num_view_textSize				|pageNumView textSize	  									|Default 10
pageNumView_site					|pageNumView site											|Default topRight
page_num_view_mark				|pageNumView mark |Default /

        <attr name="pageNumView_site">
            <enum name="topLeft" value="0" />
            <enum name="topRight" value="1" />
            <enum name="bottomLeft" value="2" />
            <enum name="bottomRight" value="3" />
            <enum name="centeredLeft" value="4" />
            <enum name="centeredRight" value="5" />
            <enum name="topCenter" value="6" />
            <enum name="bottomCenter" value="7" />
        </attr>

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
