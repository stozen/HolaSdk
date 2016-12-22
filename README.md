#HolaSDK注意事项
####这是火拉科技自己研发的sdk，包含注册和支付功能
####以后有新的东西，会慢慢更新上去
######```UC GamesSDK 注意事项```
#####问题复现方法
######1、打开游戏，打开任意一个SDK界面，如登录、账户管理、充值等
######2、使用手机home键最小化游戏到后台
######3、在手机的应用程序菜单launcher界面找到游戏图标，点击打开
######期望结果:
######之前打开sdk界面仍保持在最上层
######实际结果:
######之前打开sdk界面没有保持在最上层，已消失，出现的是游戏界面
######将androidManifest.xml文件中的```singleTask```改为```standard```
######生成签名文件的命令:```keytool -v -genkey -keystore cert.keystore -alias cert.keystore -keyalg RSA -validity 200000```
######Apk签名命令:```jarsigner -verbose -sigalg MD5withRSA -digestalg SHA1 -keystore cert.keystore -sigfile cert -signedjar xxx_new_signed.apk xxx_new.apk cert.keystore```
##### ```Downjoy Sdk 注意事项```
####1、使用360签名工具对apk进行签名，找到当乐的签名文件，输入签名密码，一键签名，就是最终的apk
####2、添加悬浮框
