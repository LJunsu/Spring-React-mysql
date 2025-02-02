import axios from "axios";
import { SignInRequestDto, SignUpRequestDto } from "./request/auth";
import { SignInResponseDto, SignUpResponseDto } from "./response/auth";
import { ResponseDto } from "./response";
import { GetSignInUserResponseDto, GetUserResponseDto, PatchNicknameResponseDto, PatchProfileImageResponseDto } from "./response/user";
import { patchBoardRequestDto, PostBoardRequestDto, PostCommentRequestDto } from "./request/board";
import { PostBoardResponseDto, GetBoardResponseDto, IncreaseViewCountResponseDto, GetFavoriteListResponseDto, GetCommentListResponseDto, PutFavoriteResponseDto, PostCommentResponseDto, DeleteBoardResponsedto, PatchBoardResponseDto, GetLatestBoardListResponseDto, GetTop3BoardListResponseDto, GetSearchBoardListResponseDto, GetUserBoardListResponseDto } from "./response/board";
import { GetPopularListResponseDto, GetRelationListResponseDto } from "./response/search";
import { PatchNicknameRequestDto, PatchProfileImageRequestDto } from "./request/user";

const DOMAIN = 'http://localhost:4000';
const API_DOMAIN = `${DOMAIN}/api/v1`;
const authorization = (accessToken : string) => {
    return {headers : {Authorization : `Bearer ${accessToken}`}};
}

const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`;

const SIGN_IN_URL = () => `${API_DOMAIN}/auth/sign-in`;

export const signInRequest = async (requestBody : SignInRequestDto) => {
    const result = 
        await axios.post(SIGN_IN_URL(), requestBody)
        .then(response => {
            const responseBody : SignInResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response.data) return null;
            const responseBody : ResponseDto = error.response.data;
            return responseBody;
        })

    return result;
}

export const signUpRequest = async (requestBody : SignUpRequestDto) => {
    const result = await axios.post(SIGN_UP_URL(), requestBody)
    .then(response => {
        const responseBody : SignUpResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response.data) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

const GET_BOADR_URL = (boardNumber : number | string) => `${API_DOMAIN}/board/${boardNumber}`;
const GET_FAVORITE_LIST_URL = (boardNumber : number | string) => `${API_DOMAIN}/board/${boardNumber}/favorite-list`
const GET_COMMENT_LIST_URL = (boardNumber : number | string) => `${API_DOMAIN}/board/${boardNumber}/comment-list`
const INCREASE_VIEW_COUNT_URL = (boardNumber : number | string) => `${API_DOMAIN}/board/${boardNumber}/increase-view-count`;
const POST_BOARD_URL = () => `${API_DOMAIN}/board`;
const POST_COMMENT_URL = (boardNumber : number | string) => `${API_DOMAIN}/board/${boardNumber}/comment`;
const PUT_FAVORITE_URL = (boardNumber : number | string) => `${API_DOMAIN}/board/${boardNumber}/favorite`;
const DELETE_BOARD_URL = (boardNumber : number | string) => `${API_DOMAIN}/board/${boardNumber}`;
const PATCH_BOARD_URL = (boardNumber : number | string) => `${API_DOMAIN}/board/${boardNumber}`;
// const GET_LATEST_BOARD_LIST_URL = () => `${API_DOMAIN}/board/latest-list`;
// const GET_TOP_3_BOARD_LIST_URL = () => `${API_DOMAIN}/board/top-3`;
// const GET_SEARCH_BOARD_LIST_URL = (searchWord : string, preSearchWord : string | null) => `${API_DOMAIN}/board/search-list/${searchWord}${preSearchWord ? '/' + preSearchWord : ''}`;
const GET_USER_BOARD_LIST_URL = (email : string) => `${API_DOMAIN}/board/user-board-list/${email}`;

export const getBoardRequest = async (boardNumber : number | string) => {
    const result = await axios.get(GET_BOADR_URL(boardNumber))
    .then(response => {
        const responseBody : GetBoardResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    })

    return result;
}

export const increaseViewCountRequest = async (boardNumber : number | string) => {
    const result = await axios.get(INCREASE_VIEW_COUNT_URL(boardNumber))
    .then(response => {
        const responseBody : IncreaseViewCountResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    })

    return result;
}

export const getFavoriteListRequest = async (boardNumber : number | string) => {
    const result = await axios.get(GET_FAVORITE_LIST_URL(boardNumber))
    .then(response => {
        const responseBody : GetFavoriteListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    })

    return result;
}

export const getCommentListRequest = async (boardNumber : number | string) => {
    const result = await axios.get(GET_COMMENT_LIST_URL(boardNumber))
    .then(response => {
        const responseBody : GetCommentListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    })
    
    return result;
}

export const postBoardRequest = async (requestBody : PostBoardRequestDto, accessToken : string) => {
    const result = await axios.post(POST_BOARD_URL(), requestBody, authorization(accessToken))
    .then(response => {
        const responseBody : PostBoardResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response.data) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    })

    return result;
}


const GET_RELATION_LIST_URL = (searchWord : string) => `${API_DOMAIN}/search/${searchWord}/relation-list`;

const GET_POPULAR_LIST_URL = () => `${API_DOMAIN}/search/popular-list`;

export const getPopularListRequest = async () => {
    const result = await axios.get(GET_POPULAR_LIST_URL())
    .then(response => {
        const responseBody : GetPopularListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

export const getRealtionListRequest = async (searchWord : string) => {
    const result = await axios.get(GET_RELATION_LIST_URL(searchWord))
    .then(response => {
        const responseBody : GetRelationListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

return result;
}

const GET_SIGN_IN_USER_URL = () => `${API_DOMAIN}/user/`;
const GET_USER_URL = (email : string) => `${API_DOMAIN}/user/${email}`;
const PATCH_NICKNAME_URL = () => `${API_DOMAIN}/user/nickname`;
const PATCH_PROFILE_IMAGE_URL = () => `${API_DOMAIN}/user/profile-image`;

export const getSignInUserRequest = async (accessToken : string) => {
    const result = await axios.get(GET_SIGN_IN_USER_URL(), authorization(accessToken))
    .then(response => {
        const responseBody : GetSignInUserResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

export const getUserRequest = async (email : string) => {
    const result = await axios.get(GET_USER_URL(email))
    .then(response => {
            const responseBody : GetUserResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response) return null;
            const responseBody : ResponseDto = error.response.data;
            return responseBody;
        });

    return result;
}

export const patchNicknameRequest = async (requestBody : PatchNicknameRequestDto, accessToken : string) => {
    const result = await axios.patch(PATCH_NICKNAME_URL(), requestBody, authorization(accessToken))
    .then(response => {
            const responseBody : PatchNicknameResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response) return null;
            const responseBody : ResponseDto = error.response.data;
            return responseBody;
        });

    return result;
}

export const patchProfileImageRequest = async (requestBody : PatchProfileImageRequestDto, accessToken : string) => {
    const result = await axios.patch(PATCH_PROFILE_IMAGE_URL(), requestBody, authorization(accessToken))
    .then(response => {
            const responseBody : PatchProfileImageResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response) return null;
            const responseBody : ResponseDto = error.response.data;
            return responseBody;
        });

    return result;
}

const FILE_DOMAIN = `${DOMAIN}/file`;

const FILE_UPLOAD_URL = () => `${FILE_DOMAIN}/upload`;
const mutipartFormData = {headers : {'Content-Type' : 'multipart/form-data'}};

export const fileUploadRequest = async (data : FormData) => {
    const result = await axios.post(FILE_UPLOAD_URL(), data, mutipartFormData)
    .then(response => {
        const responseBody : string = response.data;
        return responseBody;
    })
    .catch(error => {
        return null;
    });

    return result;
}

export const putFavoriteRequest = async (boardNumber : number | string, accessToken : string) => {
    const result = await axios.put(PUT_FAVORITE_URL(boardNumber), {}, authorization(accessToken))
    .then(response => {
        const responseBody : PutFavoriteResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

export const postCommentRequest = async (boardNumber : number | string, requestBody : PostCommentRequestDto, accessToken : string) => {
    const result = await axios.post(POST_COMMENT_URL(boardNumber), requestBody, authorization(accessToken))
    .then(response => {
        const responseBody : PostCommentResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

export const deleteBoardRequest = async (boardNumber : number | string, accessToken : string) => {
    const result = await axios.delete(DELETE_BOARD_URL(boardNumber), authorization(accessToken))
    .then(response => {
        const responseBody : DeleteBoardResponsedto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

export const patchBoardRequest = async (boardNumber : number | string, requestBody : patchBoardRequestDto, accessToken : string) => {
    const result = await axios.patch(PATCH_BOARD_URL(boardNumber), requestBody, authorization(accessToken))
    .then(response => {
        const responseBody : PatchBoardResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

const GET_LATEST_BOARD_LIST_URL = () => `${API_DOMAIN}/board/latest-list`;

export const getLatestBoardListRequest = async () => {
    const result = await axios.get(GET_LATEST_BOARD_LIST_URL())
    .then(response => {
        const responseBody : GetLatestBoardListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

const GET_TOP_3_BOARD_LIST_URL = () => `${API_DOMAIN}/board/top-3`;

export const getTop3BoardListRequest = async () => {
    const result = await axios.get(GET_TOP_3_BOARD_LIST_URL())
    .then(response => {
        const responseBody : GetTop3BoardListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

const GET_SEARCH_BOARD_LIST_URL = (searchWord : string, preSearchWord : string | null) => `${API_DOMAIN}/board/search-list/${searchWord}${preSearchWord ? '/' + preSearchWord : ''}`;

export const getSearchBoardListRequest = async (searchWord : string, preSearchWord : string | null) => {
    const result = await axios.get(GET_SEARCH_BOARD_LIST_URL(searchWord, preSearchWord))
    .then(response => {
        const responseBody : GetSearchBoardListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}

export const getUserBoardListRequest = async (email : string) => {
    const result = await axios.get(GET_USER_BOARD_LIST_URL(email))
    .then(response => {
        const responseBody : GetUserBoardListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody : ResponseDto = error.response.data;
        return responseBody;
    });

    return result;
}