import { BASE_API_URL } from "../../api/api";
import { CREATE_CHAT, CREATE_GROUP, GET_USERS_CHAT } from "./actionType";

export const createChat = (chatData) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/api/v1/chats/single`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${chatData.token}`,
      },
      body: JSON.stringify(chatData.data),
    });
    const data = await res.json();
    dispatch({ type: CREATE_CHAT, payload: data });
  } catch (error) {}
};

export const createGroupChat = (chatData) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/api/v1/chats/group`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${chatData.token}`,
      },
      body: JSON.stringify(chatData.group),
    });
    const data = await res.json();
    dispatch({ type: CREATE_GROUP, payload: data });
  } catch (error) {}
};

export const getUsersChat = (token) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/api/v1/chats/user`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });
    const data = await res.json();
    dispatch({ type: GET_USERS_CHAT, payload: data });
  } catch (error) {}
};
