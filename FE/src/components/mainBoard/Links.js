import React from 'react'
const Links = React.forwardRef((props,ref) => {
  return (
    <div className="contents">
      <div className="box category" ref={ref}>링크</div>
      <div className="box links_area">
        <div>
          <a href="https://tis.kmou.ac.kr/nxui/index.html" target='_blank'>
            <img src='https://www.kmou.ac.kr/images/ap/tm/icon/widget_icon48_basic_basic.png'alt=""/>
          </a>
          <h3>종합정보시스템</h3>
        </div>
        <div>
          <a href="https://wmail.kmou.ac.kr/index.ds" target='_blank'>
            <img src='https://www.kmou.ac.kr/images/ap/tm/icon/widget_icon55_basic_basic.png'alt=""/>
          </a>
          <h3>웹메일</h3>
        </div>
        <div>
          <a href="https://cyberweb.kmou.ac.kr/index.jsp" target='_blank'>
            <img src='https://www.kmou.ac.kr/images/ap/tm/icon/widget_icon50_basic_basic.png'alt=""/>
          </a>
          <h3>KMOU-LMS</h3>
        </div>
        <div>
          <a href="https://cts.kmou.ac.kr/" target='_blank'>
            <img src='https://www.kmou.ac.kr/images/ap/tm/icon/widget_icon49_basic_basic.png'alt=""/>
          </a>
          <h3>OCEAN-CTS</h3>
        </div>
      </div>
    </div>
  )
})

export default Links