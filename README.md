<div align="center">

# TrialQualityStandalone

[![Channel](https://img.shields.io/badge/Follow-Telegram-blue?logo=telegram)](https://t.me/Aniruf_x)
[![Download](https://img.shields.io/github/downloads/sti-233/TrialQualityStandalone/total?color=critical&label=Download&logo=data:image/png)](https://github.com/sti-233/TrialQualityStandalone/releases/latest)
[![Star](https://img.shields.io/github/stars/sti-233/TrialQualityStandalone?label=Star&color=important&logo=data:image/png)](https://github.com/sti-233/TrialQualityStandalone)

</div>

>[!Warning]
> 禁止B站站内及国内公众平台传播和宣传<br/>
> 
> 通过 lspatch 使用模块的方式不受支持<br/>

TrialQualityStandalone 是一个依照 BiliRoamingX 无限试用会员画质功能的原理，但通过非原始实现制作的 Xposed 模块。<br/>

本项目会出现:
- 无脑返回 true / false
- 推荐作用域应用并不生效
- 出现非常离谱的 ui bug

## · 参考部分
- [TrialQualityPatch.java#L22-L44](https://github.com/BiliRoamingX/BiliRoamingX/blob/main/integrations%2Fapp%2Fsrc%2Fmain%2Fjava%2Fapp%2Frevanced%2Fbilibili%2Fpatches%2FTrialQualityPatch.java#L22-L44)
- [PlayURLPlayViewUGC.kt#L58-L60](https://github.com/BiliRoamingX/BiliRoamingX/blob/main/integrations%2Fapp%2Fsrc%2Fmain%2Fjava%2Fapp%2Frevanced%2Fbilibili%2Fpatches%2Fprotobuf%2Fhooks%2FPlayURLPlayViewUGC.kt#L58-L60)
- [BangumiPlayUrlHook.kt#L110-L120](https://github.com/BiliRoamingX/BiliRoamingX/blob/main/integrations%2Fapp%2Fsrc%2Fmain%2Fjava%2Fapp%2Frevanced%2Fbilibili%2Fpatches%2Fprotobuf%2FBangumiPlayUrlHook.kt#L110-L120)
- [BangumiPlayUrlHook.kt#L512-L514](https://github.com/BiliRoamingX/BiliRoamingX/blob/main/integrations%2Fapp%2Fsrc%2Fmain%2Fjava%2Fapp%2Frevanced%2Fbilibili%2Fpatches%2Fprotobuf%2FBangumiPlayUrlHook.kt#L512-L514)

## · 源码构建
```shell
git clone https://github.com/sti-233/TrialQualityStandalone.git
cd TrialQualityStandalone
./gradlew build
```
- Windows 系统上使用 `gradlew.bat` 命令而不是 `./gradlew`
- 构建产物在 `app/release` 目录下

## Licence

[![GitHub](https://img.shields.io/github/license/sti-233/TrialQualityStandalone?style=for-the-badge)](https://github.com/sti-233/TrialQualityStandalone/blob/main/LICENSE)