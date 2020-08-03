# real-time-delivery-market

## 개요
* 코로나 19로 직접 접촉이 꺼려지는 환경에서 실시간 배송 서비스들이 많이 생기는 시기에 'E-mart, 홈플러스 등의 대형 오프라인 마트에서 어플리케이션을 이용하여 실시간 배송 서비스를 제공하면 어떨까?' 라는 생각으로 제작하게 되었습니다. 
* 실행만 되는 프로그램이 아닌 대용량 트래픽에도 문제없는 프로그램을 제작하는 것이 목표입니다.
* 프로젝트 진행정도에 따라 ReadMe와 Wiki를 업데이트할 예정입니다.

## Use case 정리

* 해당 내용은 링크를 통해 Wiki를 확인하시길 바랍니다.    
https://github.com/f-lab-edu/real-time-delivery-market/wiki/01.-Use-Case

## front 설계

* 해당 내용은 oven에서 확인하실 수 있습니다.    
https://ovenapp.io/project/G6TOhysuxWklnhi5TDYkY9WPvh7jayQ8#aGfYS

## 브랜치 관리 전략

* 해당 프로젝트는 Git-Flow 를 이용하여 브랜치를 관리하였습니다.
![git_flow](https://user-images.githubusercontent.com/54772162/88681478-92ce9600-d12c-11ea-8be4-8b516f54cc6d.png)

* master : 배포시 사용할 브랜치.    
* develop : 다음 버전을 개발하는 브랜치, 완전히 배포가 가능하다고 생각되면 master 브랜치에 merge 합니다.    
* feature : 기능을 개발하는 브랜치.    
* release : 배포를 준비할 때 사용할 브랜치.    
* hotfix : 배포 후에 발생한 버그를 수정 하는 브랜치.    

### 참고 사이트

우린 Git-flow를 사용하고 있어요, 우아한 형제들 기술 블로그, Oct 30, 2017, 나동호
https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html

## 클라우드 서버 설정
* WAS : Standard-g1(vCPU: 2개, Memory: 4GB, Disk: 50GB)
* MySQL Server : Standard-g1(vCPU: 2개, Memory: 4GB, Disk: 50GB)
* Redis Server : (산정 중)

* 서버 산정 기준에 대한 내용은 wiki에서 확인하실 수 있습니다.
https://github.com/f-lab-edu/real-time-delivery-market/wiki/02.-Cloud-Server-Setting

## CI(Continuous Integration)(예정)

