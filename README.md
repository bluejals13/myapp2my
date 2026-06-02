# myapp2my


## 설치
```bash
#inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ ls ~/.ssh
authorized_keys
inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ ssh-keygen -t ed25519 -C "bluejck10113@gmail.com"
Generating public/private ed25519 key pair.
Enter file in which to save the key (/home/inchan/.ssh/id_ed25519): 
Enter passphrase (empty for no passphrase): 
Enter same passphrase again: 
Your identification has been saved in /home/inchan/.ssh/id_ed25519
Your public key has been saved in /home/inchan/.ssh/id_ed25519.pub
The key fingerprint is:
SHA256:9nucCIvbTiXZYLWQSy7Vt5IiCxoDesH5EgKxeeuoTck bluejck10113@gmail.com
The key's randomart image is:
+--[ED25519 256]--+
|+o .    .o.      |
|oo=     +o...    |
|+o.+   +o..o .   |
|..=.o o.++o .    |
| ..= . +S.o.     |
| +..  ...+       |
|. E    ..o.o .   |
|.o    .o. ..+    |
|. .   .oo ..     |
+----[SHA256]-----+
inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ cat ~/.ssh/id_ed25519.pub
ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIFUQqdVD02Pq4mbGDHv1lrLDs70MlU7EijiSqjTEdWvo bluejck10113@gmail.com
inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ git remote set-url origin git@github.com:bluejals13/myapp2my.git
inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ git remote -v
origin	git@github.com:bluejals13/myapp2my.git (fetch)
origin	git@github.com:bluejals13/myapp2my.git (push)
inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ ssh -T git@github.com
The authenticity of host 'github.com (20.200.245.247)' can't be established.
ED25519 key fingerprint is SHA256:+DiY3wvvV6TuJJhbpZisF/zLDA0zPMSvHdkr4UvCOqU.
This key is not known by any other names.
Are you sure you want to continue connecting (yes/no/[fingerprint])? yes
Warning: Permanently added 'github.com' (ED25519) to the list of known hosts.
Hi bluejals13! You've successfully authenticated, but GitHub does not provide shell access.
inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ git push origin main
오브젝트 나열하는 중: 6026, 완료.
오브젝트 개수 세는 중: 100% (6026/6026), 완료.
Delta compression using up to 24 threads
오브젝트 압축하는 중: 100% (5905/5905), 완료.
오브젝트 쓰는 중: 100% (6022/6022), 77.49 MiB | 4.67 MiB/s, 완료.
Total 6022 (delta 1393), reused 18 (delta 1), pack-reused 0
remote: Resolving deltas: 100% (1393/1393), completed with 3 local objects.
remote: warning: See https://gh.io/lfs for more information.
remote: warning: File CI-CDpart/backend/build/libs/backend-0.0.1-SNAPSHOT.jar is 56.40 MB; this is larger than GitHub's recommended maximum file size of 50.00 MB
remote: warning: GH001: Large files detected. You may want to try Git Large File Storage - https://git-lfs.github.com.
ls -aTo github.com:bluejals13/myapp2my.git
   b70a7b1..dfca910  main -> main
inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ ls -a
.  ..  .git  .github  CI-CDpart  README.md
inchan@inchan-B650-Steel-Legend-WiFi:~/바탕화면/MLOps-2/myapp2my$ 
```
