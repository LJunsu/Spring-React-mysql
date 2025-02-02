import React from 'react'
import { FavoriteListItem } from 'types/interface'
import defaulteProfileImage from 'assets/image/default-profile-image.png';
import './style.css';

interface Props {
    favoriteListItem : FavoriteListItem
}

// component : Favorit List Item 컴포넌트
export default function FavoriteItem({favoriteListItem} : Props) {
    
    // state : properties
    const {profileImage, nickname} = favoriteListItem;
    
    // component : Favorit List Item 렌더링
    return (
        <div className='favorite-list-item'>
            <div className='favorite-list-item-profile-box'>
                <div 
                    className='favorite-list-item-profile-image'
                    style={{backgroundImage: `url(${profileImage ? profileImage : defaulteProfileImage})`}}
                ></div>
            </div>

            <div className='favorite-list-item-nickname'>{nickname}</div>
        </div>

    )
}
