# RippleEffectLayout
点击控件波纹效果，适用于95%以上控件

![img](https://github.com/mengcuiguang/RippleEffectLayout-master/blob/master/test.gif)

## 布局文件
```java
<com.meng.rippleeffectlayout.RippleEffectLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:minRadius="100"
    app:maxRadius="800"
    app:colorAlpha="0.2"
    app:color="#000000"
    app:useCenter="true"
    android:padding="16dp">
    
    <要点击的子布局>
  
</com.arjinmc.rippleeffectlayout.RippleEffectLayout>
```

## 动态设置
```java
RippleEffectLayout rippleEffectLayout = new RippleEffectLayout.Builder(this)
                .childView(textView)
                .minRadius(50)
                .maxRadius(1000)
                .color(Color.RED)
                .alpha(0.3f)
                .create();
linearLayout.addView(rippleEffectLayout);
```
