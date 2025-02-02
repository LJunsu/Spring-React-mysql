import { BoardListItem } from "types/interface";
import ResponseDto from "../response.dto";

export default interface GetLatestBoardListResponseDto extends ResponseDto {
    latestList : BoardListItem[];
}