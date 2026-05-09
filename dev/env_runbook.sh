#!/bin/bash

set -e

ls -a
ls -l

echo -e "\n ex.env -> .env 복사 \n\n ex.gitignore -> .gitignore 복사 \n"


if [ ! -f "ex.gitignore" ]; then
  echo "ex.gitignore 파일이 존재하지 않습니다."
  exit 1
fi

cp -f ex.gitignore .gitignore

if [ ! -f "ex.env" ]; then
  echo "ex.env 파일이 존재하지 않습니다."
  exit 1
fi

cp -f ex.env .env

echo "[0] .env 과 .gitignore 생성/덮어쓰기 완료"
ls -l .env
ls -l .gitignore


