import React, { useEffect, useState } from "react";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { TbCircleDashed } from "react-icons/tb";
import { BiCommentDetail } from "react-icons/bi";
import { AiOutlineSearch } from "react-icons/ai";
import {
  BsEmojiSmile,
  BsFilter,
  BsMicFill,
  BsThreeDotsVertical,
} from "react-icons/bs";
import { ImAttachment } from "react-icons/im";
import ChatCard from "../chatcard/ChatCard";
import MessageCard from "../messagecard/MessageCard";
import Profile from "../profile/Profile";
import logo from "./defaultlogo.png";
import wallpaper from "./wallpaper.jpg";
import "./HomePage.css";
import { useNavigate } from "react-router-dom";
import CreateGroup from "../group/CreateGroup";
import { useDispatch, useSelector } from "react-redux";
import { currentUser, logout, searchUser } from "../../redux/auth/action";
import { createChat, getUsersChat } from "../../redux/chat/action";
import { createMessage, getAllMessages } from "../../redux/message/action";
import SockJS from "sockjs-client/dist/sockjs";
import { over } from "stompjs";
const HomePage = () => {
  const navigate = useNavigate();
  const [messages, setMessages] = useState([]);
  const [querys, setQuerys] = useState("");
  const [currentChat, setCurrentChat] = useState(null);
  const [content, setContent] = useState("");
  const [isProfile, setIsProfile] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const [isConnect, setIsConnect] = useState(false);
  const [isGroup, setIsGroup] = useState(false);
  const dispatch = useDispatch();
  const { auth, chat, message } = useSelector((store) => store);
  const token = localStorage.getItem("token");
  const [stompClient, setStopClient] = useState();

  const connect = () => {
    const sock = new SockJS("http://localhost:8083/ws");
    const temp = over(sock);
    temp.debug=null
    setStopClient(temp);

    const headers = {
      Authorization: `Bearer ${token}`,
      "X-XSRF-TOKEN": getCookie("XSRF-TOKEN"),
    };

    temp.connect(headers, onConnect, onError);
  };

  const getCookie = () => {
    const value = `; ${document.cookie}`;
    // eslint-disable-next-line no-restricted-globals
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
      return parts.pop().split(";").shift();
    }
  };

  const onError = (error) => {
  };

  const onConnect = () => {
    setIsConnect(true);
  };

  useEffect(() => {
    if (message.newMessage && stompClient) {
      setMessages([...messages, message.newMessage]);
      stompClient?.send("/app/message", {}, JSON.stringify(message.newMessage));
    }
  }, [message.newMessage]);

  const onMessageService = (payload) => {
    const receivedMessage = JSON.parse(payload.body);
    setMessages([...messages, receivedMessage]);
  };

  useEffect(() => {
    if (isConnect && stompClient && auth.reqUser && currentChat) {
      const subscription = stompClient.subscribe(
        "/group/" + currentChat.id.toString(),
        onMessageService
      );

      return () => {
        subscription.unsubscribe();
      };
    }
  });

  useEffect(() => {
    setMessages(message.messages);
  }, [message.messages]);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleClickOnChatCard = (userId) => {
    dispatch(createChat({ token, data: { userId } }));
    setQuerys("");
  };
  const handleSearch = (keyword) => {
    dispatch(searchUser({ keyword, token }));
  };
  const handleCreateNewMessage = () => {
    dispatch(
      createMessage({
        token,
        data: { chatId: currentChat.id, content: content },
      })
    );
  };
  const handleNavigate = () => {
    setIsProfile(true);
  };

  useEffect(() => {
    dispatch(getUsersChat(token));
  }, [chat.createdChat, chat.createdGroup]);

  const handleCloseOpenProfile = () => {
    setIsProfile(false);
  };

  const handleCreateGroup = () => {
    setIsGroup(true);
  };

  const handleLogout = () => {
    dispatch(logout());
    navigate("/signin");
  };

  const handleCurrentChat = (item) => {
    setCurrentChat(item);
  };

  useEffect(() => {
    dispatch(currentUser(token));
  }, [token]);

  useEffect(() => {
    if (!auth.reqUser) {
      navigate("/signin");
    }
  }, [auth.reqUser]);

  useEffect(() => {
    if (currentChat?.id)
      dispatch(getAllMessages({ chatId: currentChat.id, token }));
  }, [currentChat, message.newMessage]);

  useEffect(() => {
    connect();
  }, []);

  return (
    <div className="relative bg-slate-500">
      <div className="w-full py-14 bg-[#00a884] ">
        <div className="flex bg-[#f0f2f5] h-[90vh] w-[95vw] absolute top-[5vh] left-[2vw]">
          <div className="left w-[30%] bg-[#e8e9ec] h-full ">
            {isGroup && <CreateGroup setIsGroup={setIsGroup} />}
            {isProfile && (
              <div className="w-full h-full">
                <Profile handleCloseOpenProfile={handleCloseOpenProfile} />
              </div>
            )}

            {!isProfile && !isGroup && (
              <div className="w-full">
                {
                  <div className="flex justify-between items-center p-3">
                    <div
                      onClick={handleNavigate}
                      className="flex items-center space-x-3"
                    >
                      <img
                        className="rounded-full w-10 h-10 cursor-pointer"
                        src={
                          auth.reqUser?.profilePicture ||
                          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
                        }
                        alt=""
                      />
                      <p>{auth.reqUser?.fullName}</p>
                    </div>
                    <div className="space-x-3 text-2xl flex">
                      <TbCircleDashed />
                      <BiCommentDetail />
                      <div>
                        <BsThreeDotsVertical
                          id="basic-button"
                          aria-controls={open ? "basic-menu" : undefined}
                          aria-haspopup="true"
                          aria-expanded={open ? "true" : undefined}
                          onClick={handleClick}
                          className="cursor-pointer"
                        />
                        <Menu
                          id="basic-menu"
                          anchorEl={anchorEl}
                          open={open}
                          onClose={handleClose}
                          MenuListProps={{
                            "aria-labelledby": "basic-button",
                          }}
                        >
                          <MenuItem onClick={handleClose}>Profile</MenuItem>
                          <MenuItem onClick={handleCreateGroup}>
                            Create group
                          </MenuItem>
                          <MenuItem onClick={handleLogout}>Logout</MenuItem>
                        </Menu>
                      </div>
                    </div>
                  </div>
                }

                <div className="relative flex justify-center items-center bg-white py-4 px-3">
                  <input
                    className="border-none outline-none bg-slate-200 rounded-md w-[93%] pl-9 py-2"
                    type="text"
                    placeholder="Search or start new chat"
                    onChange={(e) => {
                      setQuerys(e.target.value);
                      handleSearch(e.target.value);
                    }}
                    value={querys}
                  />
                  <AiOutlineSearch className="left-5 top-7 absolute" />
                  <div>
                    <BsFilter className="ml-4 text-3xl" />
                  </div>
                </div>
                <div className="bg-white overflow-y-scroll h-[72vh] px-3">
                  {querys &&
                    auth.searchUser?.map((item) => (
                      <div onClick={() => handleClickOnChatCard(item.id)}>
                        <hr />
                        <ChatCard
                          name={item.fullName}
                          userImg={
                            item.profilePicture ||
                            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
                          }
                        />
                      </div>
                    ))}

                  {chat.chats.length > 0 &&
                    !querys &&
                    chat.chats?.map((item) => (
                      <div onClick={() => handleCurrentChat(item)}>
                        <hr />
                        {item.isGroup ? (
                          <ChatCard
                            name={item.chatName}
                            userImg={
                              item.chatImage ||
                              "https://media.istockphoto.com/id/1168127003/vector/default-avatar-vector-placeholder-set-man-woman-child-teen-boy-girl-user-image-head.jpg?s=612x612&w=0&k=20&c=UulvDL4kySaaqFAkqLJjL4ggwbUvYKXbz5u1g1JZmbo="
                            }
                          />
                        ) : (
                          <ChatCard
                            isChat={true}
                            name={
                              auth.reqUser?.id !== item.users[0]?.id
                                ? item.users[0].fullNname
                                : item.users[1].fullName
                            }
                            userImg={
                              auth.reqUser.id !== item.users[0].id
                                ? item.users[0].profilePicture ||
                                  "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
                                : item.users[1].profilePicture ||
                                  "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
                            }
                          />
                        )}
                      </div>
                    ))}
                </div>
              </div>
            )}
          </div>

          {!currentChat && (
            <div className="w-[70%] flex flex-col items-center justify-center h-full">
              <div className="max-w-[70%] text-center flex items-center flex-col">
                <img className="w-[70%]" src={logo} alt="" />
                <h1 className="text-4xl text-gray-600 font-light">
                  Whatsapp Web
                </h1>
                <div className="my-9 font-light">
                  Send and receive messages without keeping your phone online.
                  <br />
                  Use WhatsApp on up to 4 linked devices and 1 phone at the same
                  time.
                </div>
              </div>
            </div>
          )}
          {currentChat && (
            <div
              className="w-[70%] relative"
              style={{ backgroundImage: `url(${wallpaper})` }}
            >
              <div className="header absolute top-0 w-full bg-[#f0f2f5]">
                <div className="flex justify-between">
                  <div className="py-3 space-x-4 flex items-center px-3">
                    <img
                      className="w-10 h-10 rounded-full"
                      src={
                        currentChat.isGroup
                          ? currentChat.chatImage ||
                            "https://media.istockphoto.com/id/1168127003/vector/default-avatar-vector-placeholder-set-man-woman-child-teen-boy-girl-user-image-head.jpg?s=612x612&w=0&k=20&c=UulvDL4kySaaqFAkqLJjL4ggwbUvYKXbz5u1g1JZmbo="
                          : auth.reqUser.id !== currentChat.users[0].id
                          ? currentChat.users[0].profilePicture ||
                            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
                          : currentChat.users[1].profilePicture ||
                            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
                      }
                      alt=""
                    />
                    <p>
                      {currentChat.isGroup
                        ? currentChat.chatName
                        : auth.reqUser?.id === currentChat.users[0].id
                        ? currentChat.users[1].fullName
                        : currentChat.user[0].fullName}
                    </p>
                  </div>
                  <div className="py-3 flex space-x-4 items-center px-3">
                    <AiOutlineSearch />
                    <BsThreeDotsVertical />
                  </div>
                </div>
              </div>
              <div className="px-10 h-[85vh] overflow-y-scroll">
                <div className="space-y-1 flex flex-col justify-center border mt-20 py-2">
                  {messages.length > 0 &&
                    messages?.map((item, i) => (
                      <MessageCard
                        isReqUserMessage={item.user.id !== auth.reqUser.id}
                        content={item.content}
                      />
                    ))}
                </div>
              </div>
              <div className="footer bg-[#f0f2f5] absolute bottom-0 w-full py-3 text-2xl">
                <div className="flex justify-between items-center px-5 relative">
                  <BsEmojiSmile className="cursor-pointer" />
                  <ImAttachment />
                  <input
                    className="py-2 outline-none border-none bg-white pl-4 rounded-md w-[85%]"
                    type="text"
                    onChange={(e) => setContent(e.target.value)}
                    placeholder="Type a message"
                    value={content}
                    onKeyPress={(e) => {
                      if (e.key === "Enter") {
                        handleCreateNewMessage();
                        setContent("");
                      }
                    }}
                  />
                  <BsMicFill />
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default HomePage;
