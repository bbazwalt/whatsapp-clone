import { applyMiddleware, combineReducers, legacy_createStore } from "redux";
import thunk from "redux-thunk";
import { authReducer } from "./auth/reducer";
import { chatReducer } from "./chat/reducer";
import { messageReducer } from "./message/reducer";
const rootReducer = combineReducers({
  auth: authReducer,
  chat: chatReducer,
  message: messageReducer,
});
export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));
