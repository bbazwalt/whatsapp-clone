import { CREATE_NEW_MESSAGE, GET_ALL_MESSAGES } from "./actionType";

const initialValue = {
  messages: [],
  newMessage: null,
};
export const messageReducer = (store = initialValue, { type, payload }) => {
  if (type === CREATE_NEW_MESSAGE) {
    return { ...store, newMessage: payload };
  } else if (type === GET_ALL_MESSAGES) {
    return { ...store, messages: payload };
  }
  return store;
};
