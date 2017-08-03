# BaseUtil
# [中文文档](https://github.com/zhangzhichaolove/BaseUtil/blob/master/README_CN.md)
 
# This framework is mainly used for Android rapid development, integration of most of the commonly used API, so that most people can integrate this Model, you can start developing immediately.
# Instructions:
Register in Application: MainInit.getInstance (). Init (this);
# Function registration:
```
  BaseConfig.setDeBug (true);// set to Bug mode, output print information.
  BaseConfig.setBackFinish (false);// Turn on the skid off function.
...
```
# The following is the API19 above support the invasion program, API greater than 19 default open (less than 19 default does not display), the status bar using View, more flexible, you can set it GONE attribute, so that the layout content extends to the status bar, or Set the color you want, such as transparent color, to be free to control the status bar color, and let the content layout reserve the status bar space, avoid setting again again android: fitsSystemWindows = "true"
![image](https://github.com/zhangzhichaolove/BaseUtil/blob/master/image/home.png) 
# Load multiple layouts:
## You can configure these layouts when the application is initialized, or you can set these properties individually in BaseActivity:
### The global configuration is as follows:
```
    CustomConfig.setLoadingView(R.layout.base_loading);
    CustomConfig.setEmptyView(R.layout.base_empty);
```
### Separate configuration as follows(Activity):
```
setLoadingRes(R.layout.base_loading);
setEmptyRes(R.layout.base_empty);
```
### Use:
```
showLoading();
showEmpty();
```
# Use of adapter:
## Whether you are ListView GridView RecyclerView you only need to integrate CustomAdapter to use.
```
        CustomAdapter adapter=new CustomAdapter<String>(mContext,list,R.layout.base_empty) {
            @Override
            public void onBind(ViewHolder holder, int viewType, int position, String item) {
                holder.view(R.id.tv_content).setText(item).setTextSize(15).setTextColor(Color.RED).setOnClickListener(this);
                holder.setText(R.id.tv_name,item);
            }

            @Override
            public void onClick(View view) {
                
            }
        };
        listView.setadapter(adapter);
        gridView.setadapter(adapter);
        recyclerView.setadapter(adapter);
```
## Multi-layout:
```
        //多布局需要构造第三个参数直接new RLItemViewType，或者在内部重写offerRLItemViewType对不同TPYE进行处理。
        CustomAdapter adapter = new CustomAdapter<String>(mContext, list, null) {
            @Override
            public void onBind(ViewHolder holder, int viewType, int position, String item) {
                if (viewType == 0) {
                    holder.view(R.id.tv_content).setText(item).setTextSize(15).setTextColor(Color.RED).setOnClickListener(this);
                } else if (viewType == 1) {
                    holder.view(R.id.tv_content).setBackgroundResource(R.drawable.common_top_back).setScaleType(ImageView.ScaleType.CENTER_CROP).setOnClickListener(this);
                }
            }

            @Override
            public void onClick(View view) {

            }

            @Override
            protected RLItemViewType<String> offerRLItemViewType() {
                return new SimpleItemType<String>() {
                    @Override
                    public int getItemViewType(int position, String s) {
                        if (position == 0) {
                            return 0;
                        } else if (position == 1) {
                            return 1;
                        }
                        return 0;
                    }

                    @Override
                    public int getLayoutId(int viewType) {
                        if (viewType == 0) {
                            return R.layout.base_empty;
                        } else if (viewType == 1) {
                            return R.layout.base_empty2;
                        }
                        return base_empty;
                    }
                };
            }
        };
```
# Add head and tail:
```
adapter.addHeader(view);
adapter.addFooter(view);
```
# Data manipulation (call these methods, do not call notifyDataSetChanged () again):
```
adapter.setData(list);
adapter.addFirst("add");
adapter.addIndex("add",2);
adapter.addLast("add");
adapter.addAll(list);
adapter.set("update",2);
adapter.remove(2);
adapter.clear();
```
# Add simulation data, when we complete the interface, you need to preview the interface without data, do not want new List () simulation data, we only need to rewrite the following methods can be resolved:
```
            @Override
            public int getBaseCount() {
                return 10;//你需要模拟的数据条数
            }
```
# Item animation:
```
SetOnlyOnce (true); // set the animation to execute once.
EnableLoadAnimation (); // Enable default transparency changes.
EnableLoadAnimation (500, animation); // define regular and animated.
EnableLoadAnimation (500, AnimationEnum.SCALE); // use the five kinds of animations provided by default.
```
# EventVus default customization of an EventBus, used exactly the same as before, but more lightweight, no need to integrate more than one lib.
# Annotate findViewById:
```
    @Id(R.id.tv_content)
    TextView tv_content;
    @Id(R.id.iv_showLoad)
    ImageView iv_showLoad;
```
# More features look forward to everyone put forward. The The
