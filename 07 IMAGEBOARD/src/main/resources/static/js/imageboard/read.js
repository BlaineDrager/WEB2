const mainImgEl = document.querySelector('.upload-box img');

const previewEls = document.querySelectorAll('#preview img'); // 여러개 이미지쪽의 유사배열
previewEls.forEach(item=>{// 하나씩 꺼내는ㄴ것
    item.addEventListener('click',function(){
        mainImgEl.src = item.src;
    })
})