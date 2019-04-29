 # BannerLayout

BannerLayout for unlimited rotation of images


> RecyclerView Banner

[RecyclerBanner](https://github.com/7449/BannerLayout/tree/RecyclerBanner)


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

![](https://github.com/7449/BannerLayout/blob/master/screenshot/banner_simple.gif)
![](https://github.com/7449/BannerLayout/blob/master/screenshot/banner_transformer.gif)
![](https://github.com/7449/BannerLayout/blob/master/screenshot/banner_refresh.gif)
![](https://github.com/7449/BannerLayout/blob/master/screenshot/banner_imagemanager.gif)

## Basic Usage

>gradle

    api 'com.ydevelop:bannerlayout:1.1.7'
    
>maven

	<dependency>
	  <groupId>com.ydevelop</groupId>
	  <artifactId>bannerlayout</artifactId>
	  <version>1.1.7</version>
	  <type>pom</type>
	</dependency>
	
	
recommended to look at Sample ： [SimpleActivity](https://github.com/7449/BannerLayout/blob/master/app/src/main/java/com/example/banner)
	
>If the network is loading pictures remember to add

	<uses-permission android:name="android.permission.INTERNET" />

>simple to use

Bean class please implement [BannerInfo](https://github.com/7449/BannerLayout/blob/master/library/src/main/java/com/bannerlayout/Listener.kt)

Specific reference [NetBannerInfo](https://github.com/7449/BannerLayout/blob/master/app/src/main/java/com/example/NetBannerInfo.kt)

If you use the built-in frame, please rely on glide

    implementation 'com.github.bumptech.glide:glide:4.9.0'
    
or

    class ImageLoaderSimpleManager : ImageLoaderManager<NetBannerInfo> {
    
        override fun display(container: ViewGroup, model: NetBannerInfo): View {
            val imageView = ImageView(container.context)
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage(model.bannerUrl, imageView)
            return imageView
        }
    }
	

see :[ImageManagerSimple](https://github.com/7449/BannerLayout/blob/master/app/src/main/java/com/example/display)


#### simple:

    banner.resource(data);
                
#### Vertical scrolling

    banner.setVertical(true)
                
#### PageChangeListener

    bannerLayout.onBannerChangeListener = object : OnBannerChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }


#### Click the event

>If you do not pass generics, the return model is the current Bean class, strong turn can be recommended to pass generics

    bannerLayout.onBannerClickListener = object : OnBannerClickListener<SimpleBannerModel> {
        override fun onBannerClick(view: View, position: Int, model: SimpleBannerModel) {
        }
    }
    
#### Tip column and small dots, title position changes

	tipsSite               	 	The location of the tip bar in the layout，top,bottom,center Three optional 
	dotsSite               		dots in the location of the prompt bar，left,center,right Three optional
	titleSite               	Title The location of the prompt bar，left,center,right Three optional

	xml:
		    <com.bannerlayout.widget.BannerLayout
		        ...
		        app:tips_site="center" />

#### Toggle animation and speed

Viewpager vertical Here is the use of animation, so long as the choice of vertical scrolling, setting animation invalid

Animation built-in [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)，Thank the author
	
If you want to customize the animation, please inherit ABaseTransformer or BannerTransformer ;
	
	banner.bannerTransformerType = BannerLayout.ANIMATION_CUBE_IN
	
	or
	
	banner.bannerTransformer = ZoomOutSlideTransformer()

#### xml 

> xml default parameter see:

        app:banner_guide="true"
        app:banner_dots_visible="true"                              
        app:banner_page_num_radius="2"                              
        app:banner_page_num_paddingLeft="10"                        
        app:banner_page_num_paddingTop="10"                         
        app:banner_page_num_paddingRight="10"                       
        app:banner_page_num_paddingBottom="10"                      
        app:banner_page_num_marginTop="10"                          
        app:banner_page_num_marginLeft="10"                         
        app:banner_page_num_marginRight="10"                        
        app:banner_page_num_marginBottom="10"                       
        app:banner_page_num_textColor="@color/colorYellow"          
        app:banner_page_num_textSize="22sp"                         
        app:banner_page_num_BackgroundColor="@color/colorYellow"    
        app:banner_page_num_mark="@string/app_name"                 
        app:banner_pageNum_site="bottomRight"                       
        app:banner_tips_site="bottom"                               
        app:banner_dots_site="center"                               
        app:banner_title_site="center"                              
        app:banner_start_rotation="true"                            
        app:banner_title_visible="true"                             
        app:banner_title_size="10sp"                                
        app:banner_title_color="@color/colorYellow"                 
        app:banner_title_width="200"                                
        app:banner_title_height="60"                                
        app:banner_title_left_margin="60"                           
        app:banner_title_right_margin="60"                          
        app:banner_dots_left_margin="40"                            
        app:banner_dots_right_margin="40"                           
        app:banner_dots_width="30"                                  
        app:banner_dots_height="30"                                 
        app:banner_enabledRadius="2"                                
        app:banner_enabledColor="@color/colorAccent"                
        app:banner_normalRadius="2"                                 
        app:banner_normalColor="@color/colorBackground"             
        app:banner_glide_error_image="@mipmap/ic_launcher"          
        app:banner_glide_place_image="@mipmap/ic_launcher"          
        app:banner_is_tips_background="true"                        
        app:banner_tips_background="@color/colorAccent"             
        app:banner_tips_width="match_parent"                        
        app:banner_tips_height="300"                                
        app:banner_dots_selector="@drawable/banner"                 
        app:banner_start_rotation="true"                            
        app:banner_delay_time="300"                                 
        app:banner_duration="3000"                                  
        app:banner_view_pager_touch_mode="true"      


License
--
    Copyright (C) 2016 yuebigmeow@gamil.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
