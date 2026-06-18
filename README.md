# 📱 KTX / SRT 자동예매 — 안드로이드 앱(APK) 만들기

NAS 웹 버전을 감싼 안드로이드 앱입니다. 설치하면 폰에 아이콘이 생기고,
탭하면 주소창 없이 앱처럼 실행됩니다.

> ⚠️ 이 앱은 **NAS 웹 서버를 화면에 띄우는 역할**만 합니다.
> 따라서 앱을 쓰려면 **NAS가 켜져 있어야** 합니다(지금 브라우저로 쓰는 것과 동일).

---

## 🟢 가장 쉬운 방법: GitHub에서 자동 빌드 (PC에 아무것도 설치 안 함)

GitHub라는 무료 사이트에 이 폴더를 올리면, **자동으로 APK를 만들어줍니다.**
컴퓨터에 안드로이드 스튜디오 같은 무거운 프로그램을 깔 필요가 없어요.

### 0단계 — 먼저 NAS 주소 확인/수정 (중요!)

`app/src/main/java/com/ktxsrt/booking/MainActivity.java` 파일을 열어
아래 줄에서 본인 NAS 주소가 맞는지 확인하세요. 다르면 고칩니다.

```java
private static final String SERVER_URL = "http://ray-disk.asuscomm.com:5080";
```

### 1단계 — GitHub 가입
[github.com](https://github.com) 에서 무료 계정을 만듭니다.

### 2단계 — 새 저장소(Repository) 만들기
1. 우측 상단 **＋ → New repository**
2. 이름 입력 (예: `ktx-srt-app`)
3. **Public** 선택 (무료 빌드는 Public이 편함)
4. **Create repository**

### 3단계 — 이 폴더 전체 업로드
1. 만든 저장소 화면에서 **"uploading an existing file"** 클릭
2. 이 폴더(`ktx-apk`) 안의 **모든 파일·폴더**를 드래그해서 업로드
   - ⚠️ `.github` 폴더가 꼭 포함되어야 합니다(숨김 폴더라 주의)
3. 아래 **Commit changes** 클릭

### 4단계 — 빌드 자동 시작 → APK 다운로드
1. 업로드하면 자동으로 빌드가 시작됩니다(약 3~5분)
2. 저장소 상단 **Actions** 탭 클릭
3. 초록 ✔ 표시가 뜨면 그 줄을 클릭
4. 맨 아래 **Artifacts → ktx-srt-app** 클릭 → zip 다운로드
5. zip 안에 **app-debug.apk** 가 들어 있습니다

> 빌드가 안 보이면 Actions 탭에서 **Build APK → Run workflow** 로 수동 실행하세요.

### 5단계 — 폰에 설치
1. APK 파일을 폰으로 전송(카톡 나에게, 구글드라이브 등)
2. 폰에서 파일 탭 → 설치
3. "출처를 알 수 없는 앱" 경고가 뜨면 **허용** (직접 만든 앱이라 안전)
4. 홈에 🚄 아이콘 생성 → 완료!

---

## 🔧 대안: 안드로이드 스튜디오로 직접 빌드

PC에 [Android Studio](https://developer.android.com/studio)가 있다면:
1. Android Studio → **Open** → 이 폴더 선택
2. 자동으로 준비될 때까지 대기(처음엔 좀 걸림)
3. 상단 메뉴 **Build → Build Bundle(s)/APK(s) → Build APK(s)**
4. 완료 알림의 **locate** 클릭 → `app/build/outputs/apk/debug/app-debug.apk`

---

## 📁 프로젝트 구성

```
ktx-apk/
├── .github/workflows/build-apk.yml   # GitHub 자동 빌드 설정
├── settings.gradle, build.gradle     # 프로젝트 설정
├── gradle.properties
└── app/
    ├── build.gradle                  # 앱 설정(패키지명/버전)
    └── src/main/
        ├── AndroidManifest.xml       # 권한·앱 정보
        ├── java/.../MainActivity.java  # ★ NAS 주소 입력하는 곳
        └── res/
            ├── layout/activity_main.xml
            ├── values/  (앱이름, 테마)
            ├── xml/network_security_config.xml  # HTTP 허용
            └── mipmap-*/ic_launcher.png         # 앱 아이콘
```

---

## ⚠️ 참고

- **NAS가 꺼져 있으면** "연결 실패" 화면이 뜹니다. NAS를 켜고 "다시 시도".
- 외부(LTE/다른 와이파이)에서 쓰려면 ASUS 라우터 **포트포워딩**이 되어 있어야 합니다(지금 브라우저로 외부 접속이 된다면 그대로 동작).
- 앱은 HTTP 접속을 허용하도록 설정되어 있습니다(NAS가 HTTP라서).
- 버전을 올리려면 `app/build.gradle`의 `versionCode`/`versionName`을 바꾸세요.
- 이 APK는 **서명 없는 디버그 빌드**라 플레이스토어 등록용은 아니지만, 개인 설치·사용에는 문제없습니다.
