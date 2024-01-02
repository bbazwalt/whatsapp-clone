import React from "react";

const ChatCard = ({ userImg, name, lastMessage, timeStamp }) => {
  return (
    <div className="flex items-center justify-center py-2 group cursor-pointer">
      <div className="w-[20%]">
        <img className="h-14 w-14 rounded-full" src={userImg} alt="" />
      </div>
      <div className=" w-[80%]">
        <div className="flex justify-between items-center">
          <p className="text-lg">{name}</p>
        </div>
        <div className="flex justify-between items-center">
          {/* <p>{lastMessage}</p> */}
          <div className="flex space-x-2 items-center">
            {/* <p className="text-sm">{ timeStamp}</p> */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatCard;
