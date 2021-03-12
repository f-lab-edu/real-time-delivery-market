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

<p align="center">
  <img src="https://user-images.githubusercontent.com/54772162/101170794-45d27180-3682-11eb-8c42-6f4bf8ec73c9.PNG?raw=true" alt="Sublime's custom image"/>
</p>

* master : 배포시 사용할 브랜치.    
* develop : 다음 버전을 개발하는 브랜치, 완전히 배포가 가능하다고 생각되면 master 브랜치에 merge 합니다.    
* feature : 기능을 개발하는 브랜치.    
* release : 배포를 준비할 때 사용할 브랜치.    
* hotfix : 배포 후에 발생한 버그를 수정 하는 브랜치.    
