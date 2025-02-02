import React, { KeyboardEvent, ChangeEvent, useRef, useState, useEffect } from 'react'
import './style.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AUTH_PATH, BOARD_PATH, BOARD_DETAIL_PATH, BOARD_UPDATE_PATH, BOARD_WRITE_PATH, MAIN_PATH, SEARCH_PATH, USER_PATH } from 'constant';
import { useCookies } from 'react-cookie';
import { useBoardStore, useLoginUserStore } from 'stores';
import { fileUploadRequest, patchBoardRequest, postBoardRequest } from 'apis';
import { patchBoardRequestDto, PostBoardRequestDto } from 'apis/request/board';
import { PatchBoardResponseDto, PostBoardResponseDto } from 'apis/response/board';
import { ResponseDto } from 'apis/response';

// component : 헤더 레이아웃
export default function Header() {

    // state : 로그인 유저 상태
    // 로그인 사용자 정보를 zustand 라이브러리로 전역 상태로 관리
    const {loginUser, setLoginUser, resetLoginUser} = useLoginUserStore();

    // state : path 상태
    // useLocation 훅을 실행하여 현재 URL의 정보를 반환
    // 반환된 객체에서 pathname 값을 구조 분해 할당으로 추출하여 
    // 현재 경로를 나타내는 pathname 변수에 저장
    const {pathname} = useLocation();

    // state : cookie 상태
    // useCookies 훅을 실행하여 쿠키를 관리할 수 있는 객체와 설정 함수를 반환
    // 반환된 객체를 cookies 변수에 저장
    // 쿠키를 업데이트할 수 있는 함수를 setCookies 변수에 저장
    const [cookies, setCookies] = useCookies();
    
    // 현재 로그인을 한 상태인지 파악하기 위한 상태
    // 로그인 상태로 어떤 버튼을 화면에 출력할 것인지 판단하기 위함
    // state : 로그인 상태
    const [isLogin, setLogin] = useState<boolean>(false);

    // state : 인증 페이지 상태
    const [isAuthPage, setAuthPage] = useState<boolean>(false);

    // state : 메인 페이지 상태
    const [isMainPage, setMainPage] = useState<boolean>(false);

    // state : 검색 페이지 상태
    const [isSearchPage, setSearchPage] = useState<boolean>(false);

    // state : 게시물 상세 페이지 상태
    const [isBoardDetailPage, setBoardDetailPage] = useState<boolean>(false);

    // state : 게시물 작성 페이지 상태
    const [isBoardWritePage, setBoardWritePage] = useState<boolean>(false);

    // state : 게시물 수정 페이지 상태
    const [isBoardUpdatePage, setBoardUpdatePage] = useState<boolean>(false);

    // state : 유저 페이지 상태
    const [isUserPage, setUserPage] = useState<boolean>(false);

    // function : 내비게이트 함수
    // useNavigate 훅을 실행하면 페이지 이동을 할 수 있게 해주는 함수를 반환
    // 반환하는 함수를 navigate 변수에 저장
    // navigate의 인자로 설정한 path값을 넘겨주면 해당 경로로 이동
    const navigate = useNavigate();

    // event handler : 로고 클릭 이벤트 처리 함수
    const onLogoClickHandler = () => {

        // App.tsx에서 설정한 Route 경로인 '/'으로 이동
        navigate(MAIN_PATH());
    }

    // component : 검색 버튼 컴포넌트
    const SearchButton = () => {

        // state : 검색어 버튼 요소 참조 상태
        // useRef 훅은 리액트에서 DOM 요소나 값을 저장하고 참조할 수 있는 객체를 생성
        // 컴포넌트의 재렌더링과 무관하게 값을 유지
        // HTMLDivElement은 타입스크립트에서 정의한 타입으로
        // HTML에서 <div>는 스크립트에서 HTMLDivElement 객체로 표현
        // 이를 통해 타입스크립트가 div에 어떤 속성과 메소드가 있는지 알려줄 수 있음
        // 이후 태그에 ref 속성으로 적용
        const searchButtonRef = useRef<HTMLDivElement | null>(null);

        // state : 검색 버튼 상태
        // 검색 버튼의 상태로 클릭 여부를 파악 - 클릭하면 검색 input을 화면에 출력
        const [status, setStatus] = useState<boolean>(false);

        // state : 검색어 상태
        // 검색 input에 입력한 값을 저장
        const [word, setWord] = useState('');

        // state : 검색어 path variable 상태
        // useParams는 React Router에서 제공하는 훅으로
        // Router에서 설정한 동적 파라미터를 객체 형태로 반환
        // `/search/${searchWord}`로 설정되어 있을 경우,
        // /search/keyword에서 searchWord는 keyword가 됨
        const {searchWord} = useParams();


        // event handler : 검색어 변경 이벤트 처리 함수
        // input에 값을 입력할 때마다 해당 input의 값을 상태에 반영
        const onSearchWordChangeHandler = (e : ChangeEvent<HTMLInputElement>) => {
            const value = e.target.value;
            setWord(value);
        }

        // event handler : 검색어 키 이벤트 처리 함수
        // KeyboardEvent<HTMLInputElement>는 input에서 발생하는 키보드 이벤트를 처리
        // 이 이벤트 객체는 key 속성이 포함되어사용자가 누른 키를 알 수 있음
        const onSearchWordKeyDownHandler = (e : KeyboardEvent<HTMLInputElement>) => {
            // 입력한 key가 Enter가 아니라면
            if(e.key !== 'Enter') return;
            
            // searchButtonRef는 useRef로 생성된 참조 변수로 DOM 요소에 데한 참조
            // current는 ref가 참조하는 DOM 요소를 가르킴
            // 아직 할당되지 않았거나, DOM 요소가 없다면(null 이라면)
            if(!searchButtonRef.current) return;

            // 해당 버튼에 대해 click() 메소드를 호출
            searchButtonRef.current.click();
        }


        // event handler : 검색 버튼 클릭 이벤트 처리 함수
        const onSearchButtonClickHandler = () => {
            
            // status가 false라면(input 활성화 X) statur를 true로 변경한 후 함수 종료
            if(!status) {
                setStatus(!status);
                return;
            }

            // status가 true라면 navigate를 통해 URL 변경
            navigate(SEARCH_PATH(word));
        }

        // effect : 검색어 path variable 변경 시 실행될 함수
        // searchWord(검색 파라미터)가 변경될 때마다
        useEffect(() => {
            
            // URL 동적 파라미터의 객체가 있다면
            if(searchWord) {
                
                // 파라미터의 객체의 값으로 word 상태를 변경
                setWord(searchWord);
                setStatus(true);
            }
        }, [searchWord])


        // status가 false라면 - 검색 버튼을 클릭하지 않은 상태로, input이 화면에 없음
        // status가 false로 클릭 시 onSearchButtonClickHandler 함수로 status 값을 true로 변경
        if(!status) {
            
            // render : 검색 버튼 컴포넌트 렌더링 (클릭 false 상태)
            return (
                <div className='icon-button' onClick={onSearchButtonClickHandler}>
                    <div className='icon search-light-icon'></div>
                </div>
            );
        }
        
        // status가 true라면 - 검색 버튼을 클릭한 상태로, input이 화면에 있음
        // status가 true 클릭 시 onSearchButtonClickHandler 함수로
        // 입력한 값을 파라미터로 URL 변경 - 검색 기능
        // render : 검색 버튼 컴포넌트 렌더링 (클릭 true 상태)
        return (
            <div className='header-search-input-box'>
                <input
                    className='header-search-input'
                    type='text'
                    placeholder='검색어를 입력해주세요.'
                    value={word}
                    onChange={onSearchWordChangeHandler}
                    onKeyDown={onSearchWordKeyDownHandler}
                />

                <div
                    ref={searchButtonRef}
                    className='icon-button'
                    onClick={onSearchButtonClickHandler}
                >
                    <div className='icon search-light-icon'></div>
                </div>
            </div>
        )
    }

    // component : 마이페이지 버튼 컴포넌트
    const MyPageButton = () => {

        // state : userEmail path variable 상태
        // useParams는 React Router에서 제공하는 훅으로
        // Router에서 설정한 동적 파라미터를 객체 형태로 반환
        // `/user/${userEmail}`로 설정되어 있을 경우,
        // /user/userEmail에서 userEmail은 사용자 아이디가 됨
        const {userEmail} = useParams();

        // event handler : 마이페이지 버튼 클릭 이벤트 처리 함수
        const onMyPageButtonClickHandler = () => {
            
            // 로그인을 한 상태가 아니라면 함수 종료
            if(!loginUser) return;

            // 로그인을 한 상태라면
            // zustand 라이브러리로 전역 변수인 loginUser에서 email을 가져옴
            const {email} = loginUser;
            
            // 가져온 email을 통해 URL 변경
            navigate(USER_PATH(email));
        }

        // event handler : 로그아웃 버튼 클릭 이벤트 처리 함수
        const onSignOutButtonClickHandler = () => {

            // 로그아웃 버튼 클릭 시
            // zustand로 전역 변수인 resetLoginUser 함수로 loginUser 값을 null로 변경
            resetLoginUser();

            setCookies('accessToken', '', {path : MAIN_PATH(), expires : new Date()});
            
            // user 정보를 제거한 후 메인 페이지로
            navigate(MAIN_PATH());
        }

        // event handler : 로그인 버튼 클릭 이벤트 처리 함수
        const onSignInButtonClickHandler = () => {

            // 로그인 버튼 클릭 시
            // auth 페이지로 이동
            navigate(AUTH_PATH());
        }

        // 현재 로그인 한 상태이며 
        // 현재 페이지(URL)의 사용자 이메일 === 현재 로그인한 사용자 이메일
        // 두 조건이 모두 true 라면
        // optional chaining(?)는
        // loginUser가 null, undefined일 경우 에러를 방지하며, email 속성에 접근
        if(isLogin && userEmail === loginUser?.email) {
            // render : 로그아웃 버튼 컴포넌트 렌더링
            return (
                <div
                    className='white-button'
                    onClick={onSignOutButtonClickHandler}
                >
                    {'로그아웃'}
                </div>
            )
        }

        // 위 로그아웃 렌더링이 실행되지 않았고
        // 현재 로그인 한 상태라면
        // isLogin이 true이며, userEmail === loginUser?.email이 false라면
        if(isLogin) {

            // render : 마이페이지 버튼 컴포넌트 렌더링
            return (
                <div
                    className='white-button'
                    onClick={onMyPageButtonClickHandler}
                >
                    {'마이페이지'}
                </div>
            )

        }

        // 위 render의 조건이 일치하지 않는다면
        // render : 로그인 버튼 컴포넌트 렌더링
        return (
            <div
                className='black-button'
                onClick={onSignInButtonClickHandler}
            >
                {'로그인'}
            </div>
        )
    }

    // component : 업로드 버튼 컴포넌트
    const UploadButton = () => {
        // state : 게시물 번호 path variable 상태
        const {boardNumber} = useParams();

        // state : 게시물 상태
        // 게시글 정보를 zustand 라이브러리로 전역 상태로 관리
        const {title, content, boardImageFileList, resetBoard} = useBoardStore();

        // function : post board response 처리 함수
        const postBoardResponse = (responseBody : PostBoardResponseDto | ResponseDto | null) => {
            if(!responseBody) return;

            const {code} = responseBody;
            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code === 'AF' || code === 'NU') navigate(AUTH_PATH());
            if(code === 'VF') alert('제목과 내용은 필수입니다.');
            if(code !== 'SU') return;

            resetBoard();
            if(!loginUser) return;
            const {email} = loginUser;
            navigate(USER_PATH(email));
        }

        // function : patch board response 처리 함수
        const patchBoardResponse = (responseBody : PatchBoardResponseDto | ResponseDto | null) => {
            if(!responseBody) return;

            const {code} = responseBody;
            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code === 'AF' || code === 'NU' || code === 'NB' || code === 'NP') navigate(AUTH_PATH());
            if(code === 'VF') alert('제목과 내용은 필수입니다.');
            if(code !== 'SU') return;

            if(!boardNumber) return;
            navigate(BOARD_PATH() + '/' + BOARD_DETAIL_PATH(boardNumber));
        }

        // event handler : 업로드 버튼 클릭 이벤트 처리
        const onUplodaButtonClickHandler = async () => {
            const accessToken = cookies.accessToken;
            if(!accessToken) return;

            const boardImageList : string[] = [];

            for(const file of boardImageFileList) {
                const data = new FormData();
                data.append('file', file);

                const url = await fileUploadRequest(data);
                if(url) boardImageList.push(url);
            }

            const isWriterPage = pathname === BOARD_PATH() + '/' + BOARD_WRITE_PATH();
            if(isWriterPage) {
                const requestBody : PostBoardRequestDto = {
                    title, content, boardImageList
                }
                postBoardRequest(requestBody, accessToken).then(postBoardResponse);
            } else {
                if(!boardNumber) return;
                const requestBody : patchBoardRequestDto = {
                    title, content, boardImageList
                }
                patchBoardRequest(boardNumber, requestBody, accessToken).then(patchBoardResponse);
            }

            // const requestBody : PostBoardRequestDto = {
            //     title, content, boardImageList
            // }

            // postBoardRequest(requestBody, accessToken).then(postBoardResponse);
        }
        
        // 전역 변수인 title과 content가 모두 true라면 - 작성했다면
        if(title && content) {
            // render : 업로드 버튼 컴포넌트 렌더링
            return (
                <div
                className='black-button'
                onClick={onUplodaButtonClickHandler}
                >
                    {'업로드'}
                </div>   
            )
        }
        
        // 전역 변수인 title과 content가 하나라도 false라면 - 하나라도 작성하지 않았다면
        // render : 업로드 불가 버튼 컴포넌트 렌더링
        return (
            <div
                className='disable-button'
            >
                {'업로드'}
            </div>   
        )

    }

    // effect : path가 변경될 때 실행
    // effect를 통해 pathname이 변경될 때 마다 실행
    useEffect(() => {
        // 현재 URL이 '/auth'로 시작하면 true, 아니라면 false
        const isAuthPage = pathname.startsWith(AUTH_PATH());
        setAuthPage(isAuthPage);

        // 현재 URL이 '/'라면 true, 아니라면 false
        const isMainPage = pathname === MAIN_PATH();
        setMainPage(isMainPage);

        // 현재 URL이 '/search'로 시작하면 true, 아니라면 false
        const isSearchPage = pathname.startsWith(SEARCH_PATH(''));
        setSearchPage(isSearchPage);

        // 현재 URL이 '/board/detail'로 시작하면 true, 아니라면 false
        const isBoardDetailPage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_DETAIL_PATH(''));
        setBoardDetailPage(isBoardDetailPage);

        // 현재 URL이 '/board/write'로 시작하면 true, 아니라면 false
        const isBoardWritePage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_WRITE_PATH());
        setBoardWritePage(isBoardWritePage);
        
        // 현재 URL이 '/board/update'로 시작하면 true, 아니라면 false
        const isBoardUpdatePage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_UPDATE_PATH(''));
        setBoardUpdatePage(isBoardUpdatePage);

        // 현재 URL이 '/user'로 시작하면 true, 아니라면 false
        const isUserPage = pathname.startsWith(USER_PATH(''));
        setUserPage(isUserPage);

    }, [pathname])

    // effect : login user가 변경될 때 마다 실행될 함수
    useEffect(() => {
        setLogin(loginUser !== null);
    }, [loginUser])

    // render : 헤더 레이아웃 렌더링
    return (
        <div id='header'>
            <div className='header-container'>
                <div className='header-left-box' onClick={onLogoClickHandler}>
                    <div className='icon-box'>
                        <div className='icon logo-dark-icon'></div>
                    </div>

                    <div className='header-logo'>{'Board'}</div>
                </div>

                <div className='header-right-box'>
                    {
                        (
                            isAuthPage 
                            || isMainPage 
                            || isSearchPage 
                            || isBoardDetailPage
                        ) && <SearchButton />
                    }
                    {
                        (
                            isMainPage 
                            || isSearchPage 
                            || isBoardDetailPage 
                            || isUserPage
                        ) && <MyPageButton />
                    }
                    {
                        (
                            isBoardWritePage 
                            || isBoardUpdatePage
                        ) && <UploadButton />
                    }
                </div>
            </div>
        </div>
    )
}
