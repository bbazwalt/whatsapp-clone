import { BASE_API_URL } from "../../config/api";
import {
  LOGIN,
  LOGOUT,
  REGISTER,
  REQ_USER,
  SEARCH_USER,
  UPDATE_USER,
} from "./actionType";

export const register = (data) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/auth/signup`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    const resData = await res.json();
    if (resData.jwt) {
      localStorage.setItem("token", resData.jwt);
    }
    console.log("register ", resData);
    dispatch({ type: REGISTER, payload: resData });
  } catch (error) {
    console.log("catch error ", error);
  }
};

export const login = (data) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    const resData = await res.json();
    if (resData.jwt) {
      localStorage.setItem("token", resData.jwt);
    }
    console.log("login ", resData);
    dispatch({ type: LOGIN, payload: resData });
  } catch (error) {
    console.log("catch error ", error);
  }
};

export const currentUser = (token) => async (dispatch) => {
  console.log("current user token", token);
  try {
    const res = await fetch(`${BASE_API_URL}/api/v1/users/profile`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });
    const resData = await res.json();
    console.log("current user ", resData);
    dispatch({ type: REQ_USER, payload: resData });
  } catch (error) {
    console.log("catch error ", error);
  }
};

export const searchUser = (data) => async (dispatch) => {
  console.log("search data",data);
  try {
    const res = await fetch(
      `${BASE_API_URL}/api/v1/users/search?name=${data.keyword}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${data.token}`,
        },
      }
    );
    const resData = await res.json();
    console.log("search user ", resData);
    dispatch({ type: SEARCH_USER, payload: resData });
  } catch (error) {
    console.log("catch error ", error);
  }
};

export const updateUser = (data, token) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/api/v1/users/update/${data.id}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });
    const resData = await res.json();
    console.log("update user ", resData);
    dispatch({ type: UPDATE_USER, payload: resData });
  } catch (error) {
    console.log("catch error ", error);
  }
};

export const logout = () => async (dispatch) => {
  localStorage.removeItem("token");
  dispatch({ type: LOGOUT, payload: null });
  dispatch({ type: REQ_USER, payload: null });
};
