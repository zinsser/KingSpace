struct CGun
{
	CString mStyle;   //�ͺ�
	CString mGunId;   //���
	CString mBullet;	//�����ӵ�
	int     mCountBullet;  //�ӵ�����

	CGun(CString style, CString gunId, CString bullet, int bulletCount)
	: mStyle(style), mGunId(gunId), mBullet(bullet), mCountBullet(bulletCount)
	{
	}
};