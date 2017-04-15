# K (Quick K)
Android IOC 快速开发框架
# use in Activity
- 在Activity中使用
```java
@KActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @KBind(R.id.tv)
    private TextView textView;
    @KBind(R.id.textView2)
    private TextView textView2;
    @KBind(R.id.textView3)
    private TextView textView3;
    @KBind(R.id.button)
    private Button button;
    @KBind(R.id.button2)
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        textView.setText("this is TextView one");
        textView2.setText("this is TextView tow");
        textView3.setText("this is TextView three");
    }

    @KListener({R.id.button2, R.id.button})
    private void showToast() {
        Toast.makeText(this, "测试按钮2", Toast.LENGTH_SHORT).show();
    }
}
```
- 在Android项目中导入
```java
Quick.jar
```
- 在Activity的类前使用
```java
@KActivity(R.layout.main)
```
- 在Activity的OnCreate方法中使用
```java 
BindClass(this) 
```
- 在View控件上使用
```java
@Kbind(R.id.textView)
```
- 设置监听回调方法
在监听回调方法前加上(默认监听使用OnClickListener)
```java
@KListener(R.id.button)
```
- 如果需要多个View控件使用同一个监听
```java
@KListener({R.id.button,R.id.button2,...})
```
- 如果需要使用其他监听回调,则注解的写法如下
```java
@KListener(value = R.id.button,listenerType = View.OnLongClickListener.class,listenerSet = "setOnLongClickListener",listenerMethod = "onLongClick")
```
# use in Fragment
- 在Fragment中使用
```java
@KFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    @KBind(R.id.button3)
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = BindClass.bind(this);
        initView();
        return view;
    }

    private void initView() {
        button.setText("测试按钮");
    }

    @KListener(R.id.button3)
    private void show() {
        Toast.makeText(getActivity(), "按钮被点击", Toast.LENGTH_SHORT).show();
    }
}
```
- 使用方法与Activity相同,只需要将
```java
@KActivity
```
- 替换为
```java
@KFragment
```
