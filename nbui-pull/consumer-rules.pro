-keep public class * extends io.dcloud.feature.uniapp.common.UniComponent{*;}
# Glide 4.10 及以后
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}