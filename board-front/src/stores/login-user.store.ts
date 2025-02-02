import { User } from "types/interface";
import { create } from "zustand";

interface LoginUserStore {
    loginUser : User | null;
    setLoginUser : (loginUser : User) => void;
    resetLoginUser : () => void;
}

// 로그인 사용자 정보를 zustand 라이브러리로 전역 상태로 관리
const useLoginUserStore = create<LoginUserStore>(set => ({
    loginUser : null
    , setLoginUser : (loginUser) => set(state => ({...state, loginUser}))
    , resetLoginUser : () => set(state => ({...state, loginUser : null}))
}));

export default useLoginUserStore;