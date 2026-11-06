CounterFlame Launcher — Android Studio project

Что делает:
- Находит установленное приложение, если его название или package id содержит "counterflame".
- Запускает найденную игру по кнопке START GAME.
- Не внедряется в игру и не меняет её файлы.

Сборка APK:
1. Установите Android Studio и Android SDK Platform 35.
2. Откройте папку CounterFlameLauncher в Android Studio.
3. Дождитесь синхронизации Gradle.
4. Выберите Build > Build Bundle(s) / APK(s) > Build APK(s).
5. APK появится в app/build/outputs/apk/debug/app-debug.apk.

Важно:
- В этой среде нет Android SDK/Gradle, поэтому готовый бинарный APK здесь не собран.
- Для обнаружения приложений используется QUERY_ALL_PACKAGES. Это удобно для установки APK напрямую; публикация в Google Play может требовать обоснования такого разрешения.
- Если игра называется иначе и её package id не содержит "counterflame", потребуется подстроить поиск в MainActivity.java.

Автоматическая сборка через GitHub Actions:
1. Создайте новый пустой репозиторий на GitHub.
2. Загрузите в него все файлы и папки этого проекта, включая скрытую папку .github/workflows.
3. Откройте вкладку Actions и дождитесь завершения workflow "Build CounterFlame Launcher APK" (или запустите его через Run workflow).
4. Откройте успешный запуск и скачайте artifact "CounterFlameLauncher" внизу страницы.
5. Распакуйте artifact ZIP — внутри будет app-debug.apk.
