package com.shoppingmall.initializer;

import com.shoppingmall.entity.Product;
import com.shoppingmall.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //상품 추가
    public void run(String... args) {
        productRepository.save(new Product("핑크 데일리 니트[가을겨울 니트 특가/부드러운/포근한]", "쉽게 올이 나가거나 늘어짐을 최소화한 아크릴 100% 탄탄한 짜임으로 제작! 저렴해보이는 얇은 두께감? 절대 X  도톰한 두께감으로 보온성을 높여줄거예요💜", 19800, 10, "상의", "top1.png"));
        productRepository.save(new Product("기모 와이드 롱팬츠[가을 신상/트레이닝/기모바지]", "넉넉한 와이드핏으로 활동하기 편하고 군살도 자연스럽게 커버되는 디자인이라 자주 손이 갈 기본 바지로 특히 강추드립니다.!", 16800, 10, "하의", "bottom1.png"));
        productRepository.save(new Product("레더 크롭 패딩[아우터 특가/주문 폭주/고퀄리티]", "겨울을 포인트 있으면서 따뜻하게 보낼 수 있는 고퀄 패딩을 가져왔어요! 하이넥으로 지퍼 끝까지 올리면 목도리 안해도 돼요!! 완전 고퀄의 레더 패딩에 안감도 고퀄로 따뜻하게🧣", 36500, 10, "아우터", "outer1.png"));
        productRepository.save(new Product("호주정품 벤딩 슬리퍼 [빠른배송/밴딩 탈부착/2-WAY]", "본사와 직접 거래하고 있으며 100% , 100% 호주산 양모이며 제조국은 중국입니다.블랙 등 어두운 색상은 물빠짐이 있을 수 있으니 검정 양말 착용을 권장드립니다", 59800, 10, "신발", "shoes1.png"));
        productRepository.save(new Product("뉴바게트백[블랙 당일출고/가을 세일]", "나일론 지퍼로 부드럽게 오픈/클로징 가능합니다. 폭이 있는 미디엄 사이즈라서 소지품 수납에도 좋습니다. 끈이 자연스럽게 떨어져 가방끈 때문에 가방이 쓰러지거나 할 일이 없어서 좋습니다.", 23000, 10, "가방", "bag1.png"));

        productRepository.save(new Product("루즈핏 카라 니트[퀄리티 높고 도톰한 원단]", "1만원대이지만, 퀄리티는 높은 탄탄 + 도톰한 원단으로 준비했어요!☃️", 18900, 20, "상의", "top2.png"));
        productRepository.save(new Product("와이드 데님팬츠[기모청바지/핀턱/기모바지]", "디테일 UP!, 힙한 분위기도~,  브러쉬 워싱으로 입체적인 느낌을 더해주고 빈티지한 무드", 24900, 10, "하의", "bottom2.png"));
        productRepository.save(new Product("크롭 숏패딩[당일출고🚀/최저가/아방핏]", "가볍고 따뜻한 크롭패딩 입니다 :) 적당한 크롭 기장에 아방핏으로 밑단 스트링과 후드 후드 스트링으로 핏조절 가능해요! 최저가에 준비해드렸으니 꼭 겟하셔서 따뜻한 겨울 보내세요♥️", 29900, 10, "아우터", "outer2.png"));
        productRepository.save(new Product("도로시 메리제인 플랫슈즈 [오늘출발/단화구두/토슈즈/하객룩]", "부드러운 벨벳원단이며 편안하게 신기 좋은 밴딩 스트랩으로 구성! 발전체 쿠션닝과 낮은 굽 부담없이 편안하게 신을 수 있는 플랫️", 29900, 10, "신발", "shoes2.png"));
        productRepository.save(new Product("3 way 구름인형키링 숄더 크로스백[스웨이드 소재/다양한 룩 활용]", "13~14인치 노트북 수납이 가능한 크기로 데일리로 언제든 착용하기 좋은 가방입니다.", 19900, 10, "가방", "bag2.png"));

        productRepository.save(new Product("레드 브이넥 니트[마켓 만족도 96%]", "1만원대이지만, 퀄리티는 높고, 어디에 입어도 잘 어울리는 모던한 니트️", 21900, 20, "상의", "top3.png"));
        productRepository.save(new Product("베이델리 조거 트레이닝 팬츠[양기모/보들촉감]", "원단 개발부텉 디자인 및 생산까지 직접 진행합니다.🤭 저희는 직접 원단을 편직하고 봉제하기 때문에 위탁 생산을 하지 않아 원단 퀄리티를 높이면서 합리적은 가격대로 보여드릴 수 있습니다. 하이웨스트 디자인으로 다리가 길어보이는 핏❤️", 18900, 10, "하의", "bottom3.png"));
        productRepository.save(new Product("데일리 스트링 크롭 패딩 조끼[도토미/데일리☃️/가성비]", "가볍고 따뜻하고 귀여운 무드의 하이넥 스트링 크롭 패딩 조끼에요. 데일리하게 컬러풀하고 가볍고 따뜻한 패딩조끼로 꾸안꾸 코디하세요!🫶", 23200, 10, "아우터", "outer3.png"));
        productRepository.save(new Product("24FW 호주어그 오즈웨어 어그[기간한정/특가/빠른배송]", "어그 부츠는 1960년대 호주 서퍼들이 바다에서 서핑을 한 후 보온용으로 신었던 양털신발에서 유래된 신발입니다. 정사이즈지만, 타이트하게 나왓습니다.", 62300, 10, "신발", "shoes3.png"));
        productRepository.save(new Product("메른 빈티지 그레이시 마블 보부상 숄더백", "자석 버튼이 있어 쉽게 클로징 가능/ 빈티지한 컬러감으로 어디에 매치해도 Good!", 29900, 10, "가방", "bag3.png"));

        productRepository.save(new Product("셔벗 니트[재입고 완료/가성비/후기증명 고퀄]", "높은 퀄리티도 챙기면서 최대한 많은 분들과 함께 입기 위해 저렴한 가격으로 제작하였습니다. 소비자부터 직원들까지 인정한 믿고 구매하는 1등 니트, 선물하기에도 제격인 셔벗 니트 추천드립니다!", 15700, 30, "상의", "top4.png"));
        productRepository.save(new Product("가을 겨울 롱팬츠[자체제작/허리들뜸X]", "4. 청량 그자체인 하늘색! 입자마자 깔끔 청순한 느낌이 들어요. 화이트톤 상의와 코디해주셔도 좋고 비슷한 블루계열 컬러로 코디하셔도 정말 매력적이랍니다. 허리쏘옥 디자인으로 허리라인 들뜸 없는 핏", 23800, 10, "하의", "bottom4.png"));
        productRepository.save(new Product("스파오 베이직 패딩 블랙[국내정품💜/SPAO️/할인중]", "무거운 아우터와 이별! 기존 930g 무게에서 889으로 무게는 가볍고 따뜻하게 만들었습니다.동물의 털을 대신하기 위해 개발한 윤리적인 패딩 충전재 3MIX SOFT!", 70000, 10, "아우터", "outer4.png"));
        productRepository.save(new Product("클래식 플랫폼 클로그 우먼 [크록스]", " 크록스 공정특성상 눌림이나 찍힘 자국이 있을수 있습니다 이는 불량이나 제품 하자가 아니므로 이로 인한 교환 또는 반품은 불가하오니 제품 구매에 참고하시기 바랍니다.", 29000, 10, "신발", "shoes4.png"));
        productRepository.save(new Product("스트라이프 테리 에코백[벨리드 made/테리원단]", "평소에 짐을 많이 들고다니는 사람에게 추천합니다. 여름에 땀이 차지 않게 이것저것 넣고 다니기 좋은 가방입니다.!", 24900, 10, "가방", "bag4.png"));
    }
}
