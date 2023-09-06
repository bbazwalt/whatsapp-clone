import { BASE_API_URL } from "../../config/api";
import { CREATE_NEW_MESSAGE, GET_ALL_MESSAGES } from "./actionType";

export const createMessage = (messageData) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/api/v1/messages/create`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${messageData.token}`,
      },
      body: JSON.stringify(messageData.data),
    });
    const data = await res.json();
    console.log("create message", data);
    dispatch({ type: CREATE_NEW_MESSAGE, payload: data });
  } catch (error) {
    console.log("catch error", error);
  }
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
    console.log("get all messages", data);
    dispatch({ type: GET_ALL_MESSAGES, payload: data });
  } catch (error) {
    console.log("catch error", error);
  }
};
