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
import { createChat } from "../../redux/chat/action";

const HomePage = () => {
  const navigate = useNavigate();
  const [querys, setQuerys] = useState(null);
  const [currentChat, setCurrentChat] = useState(false);
  const [content, setContent] = useState("");
  const [isProfile, setIsProfile] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const [isGroup, setIsGroup] = useState(false);
  const dispatch = useDispatch();
  const { auth } = useSelector((store) => store);
  const token = localStorage.getItem("token");

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleClickOnChatCard = () => {
    setCurrentChat(true);
  };
  const handleSearch = (keyword) => {
    dispatch(searchUser(keyword, token));
  };
  const handleCreateNewMessage = () => {};
  const handleNavigate = () => {
    setIsProfile(true);
  };
  const handleCreateChat = (userId) => {
    dispatch(createChat({userId}));
  };
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

  useEffect(() => {
    dispatch(currentUser(token));
  }, [token]);

  useEffect(() => {
    if (!auth.reqUser) {
      navigate("/signin");
    }
  }, [auth.reqUser]);

  return (
    <div className="relative bg-slate-500">
      <div className="w-full py-14 bg-[#00a884] ">
        <div className="flex bg-[#f0f2f5] h-[90vh] w-[95vw] absolute top-[5vh] left-[2vw]">
          <div className="left w-[30%] bg-[#e8e9ec] h-full ">
            {/* profile */}
            {isGroup && <CreateGroup />}
            {isProfile && (
              <div className="w-full h-full">
                <Profile handleCloseOpenProfile={handleCloseOpenProfile} />
              </div>
            )}

            {!isProfile && !isGroup && (
              <div className="w-full">
                {/* home */}
                {
                  <div className="flex justify-between items-center p-3">
                    <div
                      onClick={handleNavigate}
                      className="flex items-center space-x-3"
                    >
                      <img
                        className="rounded-full w-10 h-10 cursor-pointer"
                        src="https://images.pexels.com/photos/14662833/pexels-photo-14662833.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                        alt=""
                      />
                      <p>{auth.reqUser?.full_name}</p>
                    </div>
                    <div className="space-x-3 text-2xl flex">
                      <TbCircleDashed
                        className="cursor-pointer"
                        onClick={() => navigate("/status")}
                      />
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
                {/* all user */}
                <div className="bg-white overflow-y-scroll h-[72vh] px-3">
                  {querys &&
                    auth.searchUser?.map((item) => (
                      <div onClick={()=>handleClickOnChatCard(item.id)}>
                        {" "}
                        <hr />
                        <ChatCard item={item} />{" "}
                      </div>
                    ))}
                </div>
              </div>
            )}
          </div>

          {/* default whatsapp page */}
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
          {/* {message part} */}
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
                      src="https://images.pexels.com/photos/16105554/pexels-photo-16105554/free-photo-of-smiling-man-against-big-dense-foliage.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                      alt=""
                    />
                    <p>username</p>
                  </div>
                  <div className="py-3 flex space-x-4 items-center px-3">
                    <AiOutlineSearch />
                    <BsThreeDotsVertical />
                  </div>
                </div>
              </div>
              {/* {message section} */}
              <div className="px-10 h-[85vh] overflow-y-scroll">
                <div className="space-y-1 flex flex-col justify-center border mt-20 py-2">
                  {[1, 1, 1, 1, 1].map((item, i) => (
                    <MessageCard
                      isReqUserMessage={i % 2 === 0}
                      content={"message"}
                    />
                  ))}
                </div>
              </div>
              {/* footer part */}
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
