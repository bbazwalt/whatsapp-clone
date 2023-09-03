import { BASE_API_URL } from "../../config/api"

const createChat = (chatData)=> async(dispatch)=>{
    try{
        const res = await fetch(`${BASE_API_URL}/api/v1/chats/single`,{
            headers:{
                Authorization : `Bearer ${chatData.token}`,
            }
        })

    }catch(error){

    }

}