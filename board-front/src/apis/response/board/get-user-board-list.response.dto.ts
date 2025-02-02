import { BoardListItem } from "types/interface";
import ResponseDto from "../response.dto";

export default interface GetUserBoardListResponseDto extends ResponseDto {
    userBoardList : BoardListItem[];
}