# MemoApp
joyce 강의를 참고한 memo앱


사용한 기술 
1. Recycler View
2. Room 라이브러리 
  -추가시 gradle에 총 5줄 추가해줘야함 (AsyncTask 를 도와주는 라이브러리 단 , deprecated 되어서 요즘엔 코루틴으로 해야함)
   id 'kotlin-kapt'
   def room_version = "2.3.0"
   implementation ("androidx.room:room-runtime:$room_version")
   annotationProcessor "androidx.room:room-compiler:$room_version"
   kapt("androidx.room:room-compiler:$room_version")
3. onDeleteLister 인터페이스 구현 
