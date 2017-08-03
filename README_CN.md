# BaseUtil
 
# 本框架主要用于Android快速开发，集成大部分常用API，让大多数人能够集成此Model就能马上入手开发。
# 使用方法：
在Application中注册：MainInit.getInstance().init(this);
# 功能注册：
```
BaseConfig.setDeBug(true);
BaseConfig.setBackFinish(false);
....
```
# 以下是API19以上支持的沉侵方案，API大于19默认开启（低于19默认不显示），状态条使用View，更加灵活，你可以给它设置GONE属性，让布局内容延伸到状态栏，或者设置你想要的颜色，例如透明色，达到可以自由控制状态栏颜色，并让内容布局预留状态栏空间，避免再次设置android:fitsSystemWindows="true"
![image](https://github.com/zhangzhichaolove/BaseUtil/blob/master/image/home.png) 
# 加载多布局：
## 你可以在应用初始化时配置这些布局，也可以在BaseActivity单独设置这些属性：
### 全局配置如下：
```
    CustomConfig.setLoadingView(R.layout.base_loading);
    CustomConfig.setEmptyView(R.layout.base_empty);
```
### 单独配置如下(Activity)：
```
setLoadingRes(R.layout.base_loading);
setEmptyRes(R.layout.base_empty);
```
### 使用：
```
showLoading();
showEmpty();
```
# 适配器的使用：
## 无论你是ListView GridView RecyclerView 你都只需要集成CustomAdapter即可使用。
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
## 多布局：
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
# 添加头和尾：
```
adapter.addHeader(view);
adapter.addFooter(view);
```
# 数据操作(调用这些方法后，无需再次调用notifyDataSetChanged())：
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
# 添加模拟数据，当我们界面编写完成，需要预览界面而又没有数据，也不想new List()模拟数据时，我们只需重写如下方法即可解决：
```
            @Override
            public int getBaseCount() {
                return 10;//你需要模拟的数据条数
            }
```
# Item动画：
```
setOnlyOnce(true);//设置动画执行单次。
enableLoadAnimation();//启用默认透明度变化。
enableLoadAnimation(500,animation);//定义时常和动画。
enableLoadAnimation(500, AnimationEnum.SCALE);//使用默认提供的5种动画。
```
# EventVus 默认自定义了一个EventBus，使用方式与之前完全一样，但是更加轻量，无需再多集成一个lib。
# 注解findViewById：
```
    @Id(R.id.tv_content)
    TextView tv_content;
    @Id(R.id.iv_showLoad)
    ImageView iv_showLoad;
```
# 更多功能期待大家提出。。。
