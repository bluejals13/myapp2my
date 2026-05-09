# myapp2my

## 빠른 시작

```bash
# 1. 환경 변수 설정
cp .env.example .env
# .env 파일에서 DB_PASSWORD 등 수정

# 2. 빌드 
cd ~/바탕화면/myapp2my/backend
gradle wrapper --gradle-version 8.1

./gradlew clean build --stacktrace

cd ~/바탕화면/myapp2my/frontend
npm run dev
npm run build

# 3. 전체 실행
docker compose down
docker compose build --no-cache
docker-compose up --build

# 5. 확인
open http://localhost          # 앱
curl http://localhost/actuator/health  # 헬스체크
```
