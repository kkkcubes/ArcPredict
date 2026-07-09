"use client";

import {
  Bell,
  Search,
  Menu,
  Copy,
} from "lucide-react";

import { toast } from "react-hot-toast";

import ConnectWallet
  from "@/components/ConnectWallet";

import {
  useAccount,
  useChainId,
} from "wagmi";

interface TopNavbarProps {
  onMenuClick: () => void;
}

export default function TopNavbar({
  onMenuClick,
}: TopNavbarProps) {

    const {
    address,
    isConnected,
  } = useAccount();

  const chainId =
  useChainId();

  return (

    <header
  className="
    sticky
    top-0
    z-30
    bg-white
    border-b
    border-gray-200
    px-4
    md:px-6
    lg:px-8
    py-3
    flex
    items-center
    gap-3
    min-h-16
  "
>

      {/* Mobile Menu */}

      <button
        onClick={onMenuClick}
        className="
          lg:hidden
          flex-shrink-0
          h-11
          w-11
          rounded-xl
          bg-gray-100
          flex
          items-center
          justify-center
          hover:bg-gray-200
          transition-colors
        "
      >
        <Menu size={22} />
      </button>

      {/* Search */}

      <div
        className="
  flex-1
  min-w-0
  flex
  items-center
  gap-3
  rounded-xl
  border
  border-gray-200
  bg-gray-50
  px-4
  py-2
"
      >

        <Search
          size={18}
          className="
            text-gray-400
            flex-shrink-0
          "
        />

        <input
          type="text"
          placeholder="Search markets..."
          className="
            w-full
            min-w-0
            bg-transparent
            outline-none
            text-sm
            placeholder:text-gray-400
          "
        />

      </div>

      {/* Notification */}

      <button
        className="
          hidden
          md:flex
          flex-shrink-0
          h-10
          w-10
          rounded-xl
          bg-gray-100
          items-center
          justify-center
          hover:bg-gray-200
          transition-colors
        "
      >
        <Bell size={18} />
      </button>

      {/* Wallet */}

    {/* Wallet */}

<div
  className="
    flex
    items-center
    gap-4
    flex-shrink-0
  "
>

  <ConnectWallet />

</div>

    </header>

  );

}