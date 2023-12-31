import { BASE_API_URL } from "../../api/api";
import { CREATE_NEW_MESSAGE, GET_ALL_MESSAGES } from "./actionType";

export const createMessage = (reqData) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/api/v1/messages/create`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${reqData.obj.token}`,
      },
      body: JSON.stringify(reqData.obj.data),
    });
    const data = await res.json();
    reqData.sendMessageToServer(data);
    dispatch({ type: CREATE_NEW_MESSAGE, payload: data });
  } catch (error) {}
};

export const getAllMessages = (reqData) => async (dispatch) => {
  try {
    const res = await fetch(
      `${BASE_API_URL}/api/v1/messages/chat/${reqData.chatId}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${reqData.token}`,
        },
      }
    );
    const data = await res.json();
    dispatch({ type: GET_ALL_MESSAGES, payload: data });
  } catch (error) {}
};
