import React,{useState} from 'react'

const ScrolltoTop = () => {
  const [isVisible, setIsVisible] = useState(false);

  // 맨 위로가는 버튼 보이게 하기
  const handleScroll = () => {
    const scrollTop = window.scrollY;

    if(scrollTop > 200) {
      setIsVisible(true);
    } else {
      setIsVisible(false);
    }
  }

  // 버튼 클릭 시 위로 이동
  const scrolltoTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    })
  }
  
  // scroll 이벤트 발생시 보이게하는 이벤트 추가
  window.addEventListener('scroll', handleScroll);
  return (
    <div>
      <button 
        className={`scrollToTopButton ${isVisible ? 'visible' : null}`}
        onClick={scrolltoTop}><i class="fa-regular fa-circle-up"></i></button>
    </div>
  )
}

export default ScrolltoTop