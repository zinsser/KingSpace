struct CGun
{
	CString mStyle;   //型号
	CString mGunId;   //编号
	CString mBullet;	//适配子弹
	int     mCountBullet;  //子弹数量

	CGun(CString style, CString gunId, CString bullet, int bulletCount)
	: mStyle(style), mGunId(gunId), mBullet(bullet), mCountBullet(bulletCount)
	{
	}
};