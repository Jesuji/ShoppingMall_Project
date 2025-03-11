// 배너 이미지와 텍스트 배열
const bannerImages = [
    { src: 'images/products/outer2.png', title: '최저가 숏패딩', subtitle: '12월의 착한 소비' },
    { src: 'images/products/shoes1.png', title: '어그 벤딩 슬리퍼', subtitle: '#기간 한정 #밴딩 탈부착' },
    { src: 'images/products/outer1.png', title: '따뜻한 겨울 숏패딩', subtitle: '#주문 폭주 #고퀄리티' }, //쿠폰 추가하기
    { src: 'images/products/top2.png', title: '카라 니트 아노락', subtitle: '#캐주얼 #심플베이직' },
    { src: 'images/products/top4.png', title: '가성비 셔벗 니트', subtitle: '#파스텔 색상 #재입고 완료' },
];

// 현재 표시 중인 배너 인덱스
let currentBannerIndex = 0;

// HTML 요소 참조
const bannerImage = document.getElementById('banner-image');
const bannerTitle = document.getElementById('banner-title');
const bannerSubtitle = document.getElementById('banner-subtitle');

// 배너 변경 함수
function changeBanner() {
    //다음 이미지로 인덱스 변경
    currentBannerIndex = (currentBannerIndex + 1) % bannerImages.length; // 다음 이미지로 순환
    const banner = bannerImages[currentBannerIndex];

    // 이미지와 텍스트를 변경
    bannerImage.src = banner.src;
    bannerTitle.innerText = banner.title;
    bannerSubtitle.innerHTML = banner.subtitle;
}

// 2초마다 배너 자동 전환
setInterval(changeBanner, 2000);
