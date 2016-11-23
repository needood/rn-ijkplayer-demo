# 介绍

播放网易云音乐下载目录下的音频

业余玩具项目欢迎试玩

# debug

- 进入 `./android/app/build/outputs/apk` 目录
- 连上手机到pc
- 安装debug包 `adb install -r app-armv7a-debug.apk`
- 打开 rn服务器 `npm start`
- 连上手机到pc
- 绑定服务器地址到手机上 `adb reverse tcp:8080 tcp:8081`
- 打开 app
- 摇一摇
- 点选 debug server host & port for device 填写 localhost:8080
- 返回到菜单界面或者app主界面并reload js

# 编译 apk

- 进入 `./android/` 目录
- 运行 `./gradlew build -x lint`


