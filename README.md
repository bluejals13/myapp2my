

# ✔ 2. 실무 Git 선택 기준 (핵심 표)

## ✔ “언제 무엇을 써야 하는가”

| 상황            | 선택                     | 이유             |
| ------------- | ---------------------- | -------------- |
| 파일 수정 취소      | `git restore`          | 가장 안전 (로컬만 영향) |
| staging 취소    | `git restore --staged` | commit 영향 없음   |
| 로컬 커밋 되돌리기    | `git reset --soft`     | 히스토리 유지        |
| push 전 커밋 삭제  | `git reset --hard`     | 로컬만 영향         |
| push된 커밋 되돌리기 | `git revert`           | 협업 안전          |
| 실수 복구         | `git reflog`           | 최후 복구 수단       |
| 작업 임시 보관      | `git stash`            | 브랜치 이동용        |



# ✔ 3. 규모별 Git 전략 (실무 핵심)

## ✔ ① 개인 프로젝트

| 항목       | 방식                       |
| -------- | ------------------------ |
| 브랜치      | main + feature(optional) |
| merge    | 직접 merge or GitHub       |
| reset 사용 | 가능                       |
| revert   | 거의 안 씀                   |

자유도 높음



## ✔ ② 소규모 팀 (2~5명)

| 항목     | 방식             |
| ------ | -------------- |
| 브랜치    | feature / main |
| merge  | PR 권장          |
| CI     | 간단 테스트         |
| revert | 자주 사용          |

PR + 기본 협업



## ✔ ③ 일반 실무 (회사 표준)

| 항목     | 방식                       |
| ------ | ------------------------ |
| 브랜치    | feature / develop / main |
| merge  | PR 필수                    |
| CI/CD  | 자동 테스트 + 배포              |
| revert | 표준 롤백 방식                 |
| reset  | 거의 금지                    |

안정성 + 히스토리 유지



## ✔ ④ 대규모 서비스 (MSA / SaaS)

| 항목       | 방식                         |
| -------- | -------------------------- |
| 브랜치      | feature + release + hotfix |
| merge    | PR + 승인 필수                 |
| CI/CD    | 필수 + 자동 배포                 |
| revert   | 운영 필수                      |
| rollback | 배포 버전 단위                   |

안정성 최우선



# ✔ 4. 실무 사고 방식 (중요)

## ✔ Git 선택 기준

```text
안전성 > 히스토리 유지 > 속도 > 편의성
```



## ✔ 절대 원칙

* reset = 개인용
* revert = 팀용
* PR = 표준
* main = 건드리면 위험



# ✔ 5. 면접에서 자주 나오는 질문 + 답변

## Q1. reset vs revert 차이?

> reset은 히스토리를 변경하고, revert는 히스토리를 유지하면서 되돌리는 커밋을 추가합니다. 협업 환경에서는 revert를 사용합니다.



## Q2. merge conflict 해결 방법?

> 로컬에서 충돌 파일을 수정하고 add 후 commit 또는 merge --continue로 해결합니다. 이후 PR을 다시 push합니다.



## Q3. 실수했을 때 복구 방법?

> 상황에 따라 restore, reset, revert를 사용하고, 최후에는 reflog로 복구합니다.




## ✔ Git 역할 분리

| 개념     | 역할       |
| ------ | -------- |
| commit | 기록       |
| branch | 작업 단위    |
| merge  | 통합       |
| revert | 안전 롤백    |
| reset  | 로컬 되감기   |
| PR     | 협업 검수    |
| CI/CD  | 자동 검사/배포 |



