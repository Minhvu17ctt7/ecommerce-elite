import React, { useEffect, useState } from 'react'
import userApi from '../../api/userApi'
import Header from '../../components/Layout/Header'
import UserDetailComponent from '../../components/UserDetail'

const titleHeader = {
     mainTitle: 'USER',
     extraTitle: 'User Detail'
}

const UserDetail = () => {
     const [user, setUser] = useState(null);
     useEffect(() => {
          (async () => {
               try {
                    const userResponse = await userApi.getUserDetail();
                    await setUser(userResponse.data);
               } catch (e) {

               }
          })()
     }, []);
     return (
          <> <Header titleHeader={titleHeader} />
               {user && <UserDetailComponent user={user} />}
          </>
     )
}

export default UserDetail