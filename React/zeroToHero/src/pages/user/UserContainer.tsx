import axios from "axios";
import { useEffect, useState } from "react";
import userClient from "../../clients/user/UserClient";
import { bindingPageResult, PageType } from "../../components/Pagination";
import User from "./User";

export type UserType = {
    id: number, 
    username: string,
    email: string
}

export type StatesType = {userList: Array<UserType>, page: PageType}
export type CallbacksType = {
    fetchUserList: () => void;
}

const BASE_URL = "http://localhost:4000/users"
const UserContainer = () => {
    const [userList, setUserList] = useState<Array<UserType>>([])
    const [page, setPage] = useState<PageType>({hasNext:false, hasPrevious:false, size:10, page:0, totalPage:3})

    useEffect(() => {
        fetchUserList();
    }, []);

    const fetchUserById = (userId: number) => {
        const user = userClient().getById(userId);
        console.log(user)
    }

    const fetchUserList = async (username: string = "", page: number = 0) => {
        // TODO: 변수를 입력 받아 BASE_URL을 나누고 요청을 보낸다.
        try {
            let url: string = ""
            if (page !== 0) {
                url = `${BASE_URL}-page-${page}`
            }
            const response = await axios.get(url === "" ? BASE_URL : url)
            setUserList(response.data.contents)
            setPage(bindingPageResult(response))
        } catch (e) {
            if (e instanceof Error) console.error(e.message)
            else console.error(e)
        }
    }
    const callbacks: CallbacksType = {fetchUserList}
    const states: StatesType = {userList, page}
    return <User callbacks={callbacks} states={states} />
}

export default UserContainer