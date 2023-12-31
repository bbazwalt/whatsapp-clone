import React, { useEffect, useRef, useState } from "react";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { AiOutlineSearch } from "react-icons/ai";
import { BsThreeDotsVertical } from "react-icons/bs";
import ChatCard from "../components/chatcard/ChatCard";
import MessageCard from "../components/messagecard/MessageCard";
import Profile from "../components/profile/Profile";
import logo from "../assets/default-logo.png";
import blankProfilePicture from "../assets/blank-profile-picture.webp";
import blankGroupPicture from "../assets/blank-group-picture.jpg";
import wallpaper from "../assets/wallpaper.jpg";
import "../styles/Home.css";
import { Link, useNavigate } from "react-router-dom";
import CreateGroup from "../components/group/CreateGroup";
import { useDispatch, useSelector } from "react-redux";
import { currentUser, logout, searchUser } from "../redux/auth/action";
import { createChat, getUsersChat } from "../redux/chat/action";
import { createMessage, getAllMessages } from "../redux/message/action";
import SockJS from "sockjs-client";
import Stom from "stompjs";
import { BASE_API_URL } from "../api/api";
import { PiPaperPlaneRightFill } from "react-icons/pi";
const Home = () => {
  const navigate = useNavigate();
  const [messages, setMessages] = useState([]);
  const [querys, setQuerys] = useState("");
  const [currentChat, setCurrentChat] = useState(null);
  const [content, setContent] = useState("");
  const [isProfile, setIsProfile] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const [isGroup, setIsGroup] = useState(false);
  const dispatch = useDispatch();
  const { auth, chat, message } = useSelector((store) => store);
  const token = localStorage.getItem("token");

  const [stomClient, setStomClient] = useState(null);

  useEffect(() => {
    const sock = new SockJS(BASE_API_URL + "/ws");
    const stomp = Stom.over(sock);
    setStomClient(stomp);
    stomp.connect({}, onConnect, onErr);
  }, []);

  const onConnect = () => {};

  const onErr = (err) => {};

  useEffect(() => {
    if (stomClient && auth.reqUser && currentChat) {
      stomClient.subscribe(`/user/${currentChat.id}/private`, onMessageReceive);
    }
  });

  const onMessageReceive = (payload) => {
    const receivedMessage = JSON.parse(payload.body);
    setMessages([...messages, receivedMessage]);
  };

  const sendMessageToServer = (newMessage) => {
    if (stomClient && newMessage) {
      stomClient.send(
        `/app/chat/${currentChat?.id.toString()}`,
        {},
        JSON.stringify(newMessage)
      );
    }
  };

  useEffect(() => {
    setMessages(message.messages);
  }, [message.messages]);

  useEffect(() => {
    dispatch(currentUser(token));
  }, [dispatch, token]);

  useEffect(() => {
    if (!auth.reqUser) {
      navigate("/signin");
    }
  }, [auth.reqUser, navigate]);

  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: "smooth" });
    }
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      scrollToBottom();
    }, 100);
    return () => clearTimeout(timer);
  }, [messages]);

  useEffect(() => {
    if (currentChat?.id)
      dispatch(getAllMessages({ chatId: currentChat.id, token }));
  }, [currentChat, dispatch, token]);

  useEffect(() => {
    dispatch(getUsersChat(token));
  }, [chat.createdChat, chat.createdGroup, dispatch, token]);

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
    setContent("");
    const obj = {
      token,
      data: { chatId: currentChat.id, content: content },
    };
    dispatch(createMessage({ obj, sendMessageToServer }));
  };
  const handleNavigate = () => {
    handleClose();
    setIsProfile(true);
  };

  const handleCloseOpenProfile = () => {
    handleClose();
    setIsProfile(false);
  };

  const handleCloseOpenGroup = () => {
    setIsGroup(false);
  };

  const handleCreateGroup = () => {
    handleClose();
    setIsGroup(true);
  };

  const handleLogout = () => {
    handleClose();
    dispatch(logout());
    navigate("/signin");
  };

  const handleCurrentChat = (item) => {
    setCurrentChat(item);
  };

  return (
    <div className="relative bg-slate-500">
      <div className="w-full py-14 bg-[#00a884] ">
        <div className="flex bg-[#f0f2f5] h-[90vh] w-[95vw] absolute top-[5vh] left-[2vw]">
          <div className="left w-[30%] bg-[#e8e9ec] h-full ">
            {isGroup && (
              <CreateGroup
                setIsGroup={setIsGroup}
                onClick={handleCloseOpenGroup}
              />
            )}
            {isProfile && (
              <div className="w-full h-full">
                <Profile handleCloseOpenProfile={handleCloseOpenProfile} />
              </div>
            )}

            {!isProfile && !isGroup && (
              <div className="w-full ">
                {
                  <div className="flex justify-between items-center p-3">
                    <div
                      onClick={handleNavigate}
                      className="flex items-center space-x-3"
                    >
                      <img
                        className="rounded-full w-10 h-10 cursor-pointer"
                        src={
                          auth.reqUser?.profilePicture || blankProfilePicture
                        }
                        alt=""
                      />
                      <p className="cursor-pointer">{auth.reqUser?.fullName}</p>
                    </div>
                    <div className="space-x-3 text-2xl flex">
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
                    className="border-none outline-none bg-slate-200 rounded-md w-[97%] pl-16 py-2"
                    type="text"
                    placeholder="Search or start new chat"
                    onChange={(e) => {
                      setQuerys(e.target.value);
                      handleSearch(e.target.value);
                    }}
                    value={querys}
                  />
                  <AiOutlineSearch className="left-7 right-3 top-7 absolute" />
                </div>
                <div className="bg-white overflow-y-scroll h-[72vh] px-3">
                  {querys &&
                    auth.searchUser?.map((item) => (
                      <div onClick={() => handleClickOnChatCard(item.id)}>
                        <hr />
                        <ChatCard
                          name={item.fullName}
                          userImg={item.profilePicture || blankProfilePicture}
                        />
                      </div>
                    ))}

                  {chat.chats.length > 0 &&
                    !querys &&
                    chat.chats?.map((item) => (
                      <div
                        key={item.id}
                        onClick={() => handleCurrentChat(item)}
                      >
                        <hr />
                        {item.group ? (
                          <ChatCard
                            name={item.chatName}
                            userImg={item.chatImage || blankGroupPicture}
                            lastMessage={messages[messages.length - 1]?.content}
                            timeStamp={messages[messages.length - 1]?.timeStamp}
                          />
                        ) : (
                          <ChatCard
                            isChat={true}
                            name={
                              auth.reqUser?.id !== item.users[0]?.id
                                ? item.users[0].fullName
                                : item.users[1].fullName
                            }
                            userImg={
                              auth.reqUser.id !== item.users[0].id
                                ? item.users[0].profilePicture ||
                                  blankProfilePicture
                                : item.users[1].profilePicture ||
                                  blankProfilePicture
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
                  Download WhatsApp for Windows
                </h1>
                <div>
                  <div className="my-9 font-light">
                    Make calls, share your screen and get a faster experience
                    when you download the
                    <br />
                    Windows app.
                  </div>
                  <div>
                    <Link
                      to={
                        "ms-windows-store://pdp/?productid=9NKSQGP7F2NH&mode=mini&cid=68a40a"
                      }
                    >
                      <button className="rounded-full bg-green-700 px-6 py-2 text-white font-semibold	">
                        Get the app
                      </button>
                    </Link>
                  </div>
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
                        currentChat.group
                          ? currentChat.chatImage || blankGroupPicture
                          : auth.reqUser.id !== currentChat.users[0].id
                          ? currentChat.users[0].profilePicture ||
                            blankProfilePicture
                          : currentChat.users[1].profilePicture ||
                            blankProfilePicture
                      }
                      alt=""
                    />
                    <p>
                      {currentChat.group
                        ? currentChat.chatName
                        : auth.reqUser?.id === currentChat.users[0].id
                        ? currentChat.users[1].fullName
                        : currentChat.users[0].fullName}
                    </p>
                  </div>
                  <div className="py-3 flex space-x-4 items-center px-3"></div>
                </div>
              </div>
              <div className="px-10 h-[85vh] overflow-y-scroll">
                <div className="space-y-1 flex flex-col justify-center mt-20 py-2">
                  {messages.length > 0 &&
                    messages?.map((item, i) => (
                      <MessageCard
                        key={i}
                        isReqUserMessage={item.user.id !== auth.reqUser.id}
                        content={item.content}
                      />
                    ))}
                  <div ref={messagesEndRef} />
                </div>
              </div>
              <div className="footer bg-[#f0f2f5] absolute w-full py-3 text-2xl">
                <div className="flex justify-between items-center px-5 relative">
                  <input
                    className="py-2 outline-none border-none bg-white text-lg pl-4 rounded-md w-[95%]"
                    type="text"
                    onChange={(e) => setContent(e.target.value)}
                    placeholder="Type a message"
                    value={content}
                    onKeyPress={(e) => {
                      if (e.key === "Enter") {
                        handleCreateNewMessage();
                      }
                    }}
                  />
                  <button onClick={handleCreateNewMessage}>
                    <PiPaperPlaneRightFill />
                  </button>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Home;
