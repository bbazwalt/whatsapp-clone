import React from "react";

const ChatCard = ({item}) => {
  return (
    <div className="flex items-center justify-center py-2 group cursor-pointer">
      <div className="w-[20%]">
        <img
          className="h-14 w-14 rounded-full"
          src={item.profile_picture || "https://images.pexels.com/photos/17509463/pexels-photo-17509463/free-photo-of-man-woman-summer-hiking.jpeg"}
          alt=""
        />
      </div>
      <div className="pl-5 w-[80%]">
        <div className="flex justify-between items-center">
          <p className="text-lg">{item.full_name}</p>
          <p className="text-sm">timestamp</p>
        </div>
        <div className="flex justify-between items-center">
            <p>message...</p>
            <div className="flex space-x-2 items-center">
                <p className="text-xs py-1 px-2 text-white bg-green-500 rounded-full">5</p>
            </div>
        </div>
      </div>
    </div>
  );
};

export default ChatCard;
