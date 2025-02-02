import ResponseDto from "../response.dto";

export default interface SignInResponeDto extends ResponseDto {
    token : String;
    expirationTime : number;
}