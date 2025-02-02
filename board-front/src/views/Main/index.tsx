import React, { useEffect, useState } from 'react'
import './style.css';
import Top3Item from 'components/Top3Item';
import { BoardListItem } from 'types/interface';
import { latestBoardListMock, top3BoardListMock } from 'mocks';
import BoardItem from 'components/BoardItem';
import Pagination from 'components/Pagenation';
import { useNavigate } from 'react-router-dom';
import { SEARCH_PATH } from 'constant';
import { getLatestBoardListRequest, getPopularListRequest, getTop3BoardListRequest } from 'apis';
import { GetLatestBoardListResponseDto, GetTop3BoardListResponseDto } from 'apis/response/board';
import { ResponseDto } from 'apis/response';
import { usePagination } from 'hooks';
import { GetPopularListResponseDto } from 'apis/response/search';

// component : 메인 화면 컴포넌트
export default function Main() {
    // function : 내비게이트 함수
    const navigate = useNavigate();

    // component : 메인 화면 상단 컴포넌트
    const MainTop = () => {

        // state : 주간 top3 게시물 리스트 상태
        const [top3BoardList, setTop3BoardList] = useState<BoardListItem[]>([]);

        // function : get top 3 board list response 처리 함수
        const getTop3BoardListResponse = (responseBody : GetTop3BoardListResponseDto | ResponseDto | null) => {
            if(!responseBody) return;
            const {code} = responseBody;
            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code !== 'SU') return;

            const {top3List} = responseBody as GetTop3BoardListResponseDto;
            setTop3BoardList(top3List);
        }

        // effect : 첫 마운트 시 실행될 함수
        useEffect(() => {
            getTop3BoardListRequest().then(getTop3BoardListResponse);
        }, []);
        
        // render : 메인 화면 상단 컴포넌트 렌더링
        return (
            <div id='main-top-wrapper'>
                <div className='main-top-container'>
                    <div className='main-top-title'>
                        {'Board에서\n 다양한 이야기를 나눠보세요.'}
                    </div>

                    <div className='main-top-contents-box'>
                        <div className='main-top-contents-title'>
                            {'주간 TOP 3 게시글'}
                        </div>

                        <div className='main-top-contents'>
                            {
                                top3BoardList.map(top3ListItem =>
                                    <Top3Item key={top3ListItem.boardNumber} top3ListItem={top3ListItem} />
                                )
                            }
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    // component : 메인 화면 하단 컴포넌트
    const MainBottom = () => {

        // state : 페이지네이션 관련 상태
        const {
            currentPage
            , setCurrentPage
            , currentSection
            , setCurrentSection
            , viewList
            , setViewList
            , viewPageList
            , totalSection
            , setTotalList
        } = usePagination<BoardListItem>(5);

        // state : 인기 검색어 리스트 상태
        const [populerWordList, setPopulerWordList] = useState<string[]>([]);
        
        // function : get latest board list response 처리 함수
        const getLatestBoardListResponse = (responseBody : GetLatestBoardListResponseDto | ResponseDto | null) => {
            if(!responseBody) return;
            const {code} = responseBody;
            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code !== 'SU') return;

            const {latestList} = responseBody as GetLatestBoardListResponseDto;
            setTotalList(latestList);
        }

        // function : get popular list response 처리 함수
        const getPopularListResponse = (responseBody : GetPopularListResponseDto | ResponseDto | null) => {
            if(!responseBody) return;
            const {code} = responseBody;
            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code !== 'SU') return;

            const {popularWordList} = responseBody as GetPopularListResponseDto;
            setPopulerWordList(popularWordList);
        }

        // event handler : 인기 검색어 클릭 이벤트 처리
        const onPopularWordClickHandler = (word : string) => {
            navigate(SEARCH_PATH(word));
        }

        // effect : 첫 마운트 시 실행될 함수
        useEffect(() => {
            getLatestBoardListRequest().then(getLatestBoardListResponse);
            getPopularListRequest().then(getPopularListResponse);
        }, [])
        
        // render : 메인 화면 하단 컴포넌트 렌더링
        return (
            <div>
                <div id='main-bottom-wrapper'>
                    <div className='main-bottom-container'>
                        <div className='main-bottom-title'>
                            {'최신 게시물'}
                        </div>

                        <div className='main-bottom-contents-box'>
                            <div className='main-bottom-current-contents'>
                                {
                                    viewList.map(boardListItem => 
                                        <BoardItem key={boardListItem.boardNumber} boardListItem={boardListItem} />
                                    )
                                }
                            </div>

                            <div className='main-bottom-popular-box'>
                                <div className='main-bottom-popular-card'>
                                    <div className='main-bottom-popular-card-container'>
                                        <div className='main-bottom-popular-card-title'>
                                            {'인기 검색어'}
                                        </div>

                                        <div className='main-bottom-popular-card-contents'>
                                            {
                                                populerWordList.map((word, index) => 
                                                    <div 
                                                        key={index}
                                                        className='word-badge'
                                                        onClick={() => onPopularWordClickHandler(word)}
                                                    >{word}</div>
                                                )
                                            }
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className='main-bottom-pagination-box'>
                            <Pagination
                                currentPage={currentPage}
                                currentSection={currentSection}
                                setCurrentPage={setCurrentPage}
                                setCurrentSection={setCurrentSection}
                                viewPageList={viewPageList}
                                totalSection={totalSection}
                            />
                        </div>
                    </div>
                </div>
            </div>
        )
    }
    
    // render : 메인 화면 컴포넌트 렌더링
    return (
        <>
            <MainTop />

            <MainBottom />
        </>
    )
}
