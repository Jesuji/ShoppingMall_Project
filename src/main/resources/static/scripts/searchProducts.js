document.addEventListener("DOMContentLoaded", () => {
    window.searchProducts = async function () {
        const keyword = document.getElementById("search-input").value.trim();

        // 검색어가 입력되지 않았을 경우 경고 메시지 표시
        if (!keyword) {
            alert("검색어를 입력해주세요.");
            return;
        }

        // 검색어를 URL 쿼리 파라미터에 추가하여 페이지 리다이렉트
        window.location.href = `/main?keyword=${encodeURIComponent(keyword)}`;

        const productListContainer = document.querySelector(".product-list");

        // 기존 검색 결과 초기화
        productListContainer.innerHTML = '';

        try {
            const response = await fetch(`/search?keyword=${encodeURIComponent(keyword)}`);
            if (response.ok) {
                const products = await response.json();

                if (products.length === 0) {
                    productListContainer.innerHTML = '<p>검색 결과가 없습니다.</p>';
                    return;
                }

                products.forEach(product => {
                    const productCard = document.createElement('div');
                    productCard.className = 'product-item';
                    productCard.innerHTML = `
                        <a href="/products/${product.id}" class="product-link">
                            <div class="product-image-container">
                                <img src="/images/products/${product.imagePath}" alt="${product.name}" class="product-image">
                            </div>
                        </a>
                        <div class="product-info">
                            <h3 class="product-name">${product.name}</h3>
                            <p class="product-price">${product.price.toLocaleString()}원</p>
                        </div>
                    `;
                    productListContainer.appendChild(productCard);
                });
            } else {
                productListContainer.innerHTML = '<p>검색 중 오류가 발생했습니다.</p>';
            }
        } catch (error) {
            productListContainer.innerHTML = '<p>서버와의 연결에 문제가 발생했습니다.</p>';
            console.error('Error:', error);
        }
    };
});
