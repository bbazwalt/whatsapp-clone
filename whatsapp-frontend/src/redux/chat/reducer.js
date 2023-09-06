import { CREATE_CHAT, GET_USERS_CHAT } from "./actionType";

const initialValue = {
  chats: [],
  createdGroup: null,
  createdChat: null,
};
export const chatReducer = (store = initialValue, { type, payload }) => {
  if (type === CREATE_CHAT) {
    return { ...store, createdChat: payload };
  } else if (type === CREATE_CHAT) {
    return { ...store, createdGroup: payload };
  } else if (type === GET_USERS_CHAT) {
    return { ...store, chats: payload };
  }
  return store;
};
