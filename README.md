# RippleEffectLayout
RippleEffectLayout like Android 5.0 ripple effect for button, support every view.

![img](sample.gif)

## xml way
```java
<com.arjinmc.rippleeffectlayout.RippleEffectLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:minRadius="100"
    app:maxRadius="800"
    app:colorAlpha="0.2"
    app:color="#000000"
    app:useCenter="true"
    android:padding="16dp">
    <CheckBox
        android:id="@+id/check"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="ATTENTION!"
        />
</com.arjinmc.rippleeffectlayout.RippleEffectLayout>
```

## java code way
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