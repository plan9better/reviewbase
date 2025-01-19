import { useState, useEffect } from 'react';

export function useUser() {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    const logout = () => {
        localStorage.removeItem('user');
        setUser(null);
    };

    return { user, setUser, logout };
}
