		const formData = new FormData();	//폼관련 정보 저장 // inputType에 맞게 넣어주면 데이터를 저장

		const uploadBox_el = document.querySelector('.upload-box');
		//dragenter / dragover /dragleave / drop

		uploadBox_el.addEventListener('dragenter',function(e){
			e.preventDefault();
			console.log("dragenter...");
		});
		uploadBox_el.addEventListener('dragover',function(e){
			e.preventDefault();
			uploadBox_el.style.opacity='0.5';
			console.log("dragover...");

		});
		uploadBox_el.addEventListener('dragleave',function(e){
			e.preventDefault();
			uploadBox_el.style.opacity='1';
			console.log("dragleave...");

		});

        //----------------------------------------------
        //
        //----------------------------------------------
		uploadBox_el.addEventListener('drop',function(e){ // 드래그하고 드랍하는 시점
			e.preventDefault(); // 기본동작 제거
			console.log("drop...");
			console.log(e);
			console.log(e.dataTransfer);
			console.log(e.dataTransfer.files[0]);


            // fileReader 객체 만들기 // 프리뷰 기본작업 유효성 체크 다음까지 포함
            // const file = e.dataTransfer.files[0]; // 파일 정보 가지고 오기

            // 유효성 체크 filter, map
            const imgFiles = Array.from(e.dataTransfer.files).filter(f => f.type.startsWith('image/'));
            if(imgFiles.length === 0) {
                alert("이미지 파일만 가능합니다.")
                return false;
            }
            // 이미지의 개수 5개 제한하고
            // 이미지 하나당 사이즈 제한 도 가능
            imgFiles.forEach(file => {
                if(file.size > (1024*1024*5)) {
                    alert("파일 하나당 최대 사이즈는 5Mb 이하여야 합니다")
                    return false;
                }
            })

            const reader = new FileReader();
            for(var file of imgFiles) { // 이미지 파일들을 하나씩 꺼내와서
                reader.readAsDataURL(file); // reader에 파일의 정보를 넣기
                reader.onload = function(e){
                    const preview = document.querySelector('#preview'); // ElementId로 찾아도 됨 // preview 아이디 찾기
                    const imgEl = document.createElement('img');
                    console.log("reader.onload",e);
                    imgEl.setAttribute('src',e.target.result); // 소스 경로 넣기 // 기존에 있는 경우면 img.souse 를 치면됨 // e.target 타겟은 file 이다
                    preview.appendChild(imgEl);
                }
                formData.append('files',file) // 백엔드에 전달하기 위해 form 에 넣는 것 // files : file로 키와 값 처리
                console.log("formData : ", formData);
            }

		});


		const add_product_btn_el = document.querySelector('.add_product_btn');
		add_product_btn_el.addEventListener('click',function(){ // 해당 이벤트를 클릭했을 떄의 함수 처리
            const seller = document.imageform.seller.value; // html 의 정보를 가지고 오기
            const productname = document.imageform.productname.value;
            const category = document.imageform.category.value;
            const brandname = document.imageform.brandname.value;
            const itemdetals = document.imageform.itemdetals.value;
            const amount = document.imageform.amount.value;
            const size = document.imageform.size.value;

            formData.append('seller',seller);
            formData.append('productname',productname);
            formData.append('category',category);
            formData.append('brandname',brandname);
            formData.append('itemdetals',itemdetals);
            formData.append('amount',amount);
            formData.append('size',size);

            axios.post('/imageboard/add', formData, {header : {'Content-Type' : 'multipart/form-data'}}) // /imageboard/add POST로 던짐 // 'params'엔 formData를 넣고 //  'header'엔 {header : {'Content-Type' : 'multipart/form-data'}}를 넣음 // multipart 를 통해 파일 정보를 조각 내어서 전달하게 만들어 후에 어셈블 시킴
                .then(res => {console.log(res);})
                .catch(err => {console.log(err);});
		})
